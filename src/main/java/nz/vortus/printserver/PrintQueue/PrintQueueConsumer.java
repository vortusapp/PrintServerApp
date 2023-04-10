package nz.vortus.printserver.PrintQueue;


import java.awt.print.PrinterException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
        import java.util.Properties;

import nz.vortus.printserver.printers.PDFPrinter;
import org.apache.kafka.clients.consumer.Consumer;
        import org.apache.kafka.clients.consumer.ConsumerConfig;
        import org.apache.kafka.clients.consumer.ConsumerRecord;
        import org.apache.kafka.clients.consumer.ConsumerRecords;
        import org.apache.kafka.clients.consumer.KafkaConsumer;
        import org.apache.kafka.common.serialization.StringDeserializer;

public class PrintQueueConsumer {
    private static final String BOOTSTRAP_SERVERS = "localhost:9092";
    private static final String TOPIC_NAME = "test";

    public static void startPrintQueueConsumer() throws IOException, PrinterException {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "my-group");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        Consumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList(TOPIC_NAME));

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(1000);
            for (ConsumerRecord<String, String> record : records) {
                System.out.printf("Received message with key='%s' and value='%s'%n",
                        record.key(), record.value());
                Path tempFilePath = Files.createTempFile("temp-", "-" +" file .pdf");

// Write the PDF data to the temporary file
                Files.write(tempFilePath, record.value().getBytes());

// Print the path to the temporary file
                System.out.println("PDF saved to " + tempFilePath.toString());
                new PDFPrinter(new File(tempFilePath.toUri()));
            }
        }
    }
}
