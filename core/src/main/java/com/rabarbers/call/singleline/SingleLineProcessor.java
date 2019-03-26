package com.rabarbers.call.singleline;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public abstract class SingleLineProcessor<T> {
    private InputStream inputStream;
    private File file;

    public SingleLineProcessor(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public SingleLineProcessor(File file) {
        this.file = file;
    }

    public List<T> readAll() {
        if (inputStream != null) {
            return readAllFromStream();
        } else {
            return readAllFromFile();
        }
    }

    private List<T> readAllFromStream() {
        List<T> result = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                T item = convert(line);
                if (item != null) {
                    result.add(item);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    private List<T> readAllFromFile() {
        List<T> result = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
            String line;
            while ((line = br.readLine()) != null) {
                result.add(convert(line));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    protected abstract T convert(String string);
}
