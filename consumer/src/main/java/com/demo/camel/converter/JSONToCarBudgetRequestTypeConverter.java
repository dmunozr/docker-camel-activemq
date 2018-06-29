package com.demo.camel.converter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedHashMap;

import javax.xml.bind.JAXBException;

import com.google.common.base.Charsets;

import net.minidev.json.JSONObject;

import org.apache.camel.Converter;
import org.apache.camel.TypeConverters;
import org.apache.commons.io.IOUtils;

@Converter
public class JSONToCarBudgetRequestTypeConverter implements TypeConverters {

    @Converter
    public static java.io.InputStream toCarBudgetRequest(final LinkedHashMap linkedHashMap) throws JAXBException, IOException {
        final JSONObject json = new JSONObject();
        json.putAll(linkedHashMap);

        return IOUtils.toInputStream(json.toString(), Charsets.UTF_8.toString());
    }

    private static InputStream toInputStream(final Object obj) throws IOException {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(obj);
        oos.flush();
        oos.close();

        return new ByteArrayInputStream(baos.toByteArray());
    }

}
