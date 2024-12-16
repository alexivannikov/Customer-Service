package com.mycompany.interviewtask.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Collection;
import java.util.List;

/**
 * Класс утилитных методов
 */
@Slf4j
public class CommonUtils {
    static ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true);
    }

    /**
     * Преобразование данных из файла в объект
     *
     * @param file         файл для считывания
     * @param contentType  класс модели
     * @param isCollection флаг преобразования в коллекцию
     * @return объект
     */
    public static <T> T getDataFromFile(MultipartFile file,
                                        Class<?> contentType,
                                        boolean isCollection) throws IOException {

        try {
            if (isCollection) {
                return objectMapper.readValue(
                        new String(file.getBytes()),
                        objectMapper.getTypeFactory().constructCollectionType(List.class, contentType)
                );
            } else {
                return objectMapper.readValue(
                        new String(file.getBytes()),
                        objectMapper.getTypeFactory().constructType(contentType)
                );
            }
        } catch (IOException ex) {
            log.error(ex.getMessage());
            throw ex;
        }
    }

    /**
     * Запись строки в поток и его преобразование в байтовый массив
     *
     * @param string строка для записи
     * @return байтовый массив с содержимым строки
     */
    public static byte[] writeStringToOutputStream(String string) throws IOException {
        var baos = new ByteArrayOutputStream();
        var writer = new BufferedWriter(new OutputStreamWriter(baos));
        writer.write(string);
        writer.close();
        return baos.toByteArray();
    }

    /**
     * Настройка HTTP-заголовков для запроса скачивания файла
     *
     * @param fileName имя файла
     * @param fileType формат файла
     * @return объект HTTP-заголовков
     */
    public static HttpHeaders buildHttpHeaders(String fileName, String fileType) {
        var headers = new HttpHeaders();
        headers.setContentDispositionFormData("filename", String.format("%s.%s", fileName, fileType));
        return headers;
    }

    /**
     * Преобразование коллекции объектов в строку с очисткой
     * лишних символов и добавлением переносов
     *
     * @param collection коллекция
     * @return строка
     */
    public static <T> String convertCollectionToString(Collection<T> collection) {
        return collection.toString()
                .replace("[", "")
                .replace("]", "")
                .replace(" ", "")
                .replace(",", "\n");
    }
}
