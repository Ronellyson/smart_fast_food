package com.ronellyson.smart_fast_food.data.utils;

import androidx.annotation.Nullable;

import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

public class SVGConverterFactory extends Converter.Factory {

    public static SVGConverterFactory create() {
        return new SVGConverterFactory();
    }

    @Nullable
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(
            Type type,
            Annotation[] annotations,
            Retrofit retrofit
    ) {
        if (type == SVG.class) {
            return new SVGResponseBodyConverter();
        }
        return null;
    }

    private static class SVGResponseBodyConverter implements Converter<ResponseBody, SVG> {

        @Override
        public SVG convert(ResponseBody responseBody) throws IOException {
            try {
                InputStream inputStream = responseBody.byteStream();
                return SVG.getFromInputStream(inputStream);
            } catch (SVGParseException e) {
                throw new RuntimeException(e);
            } finally {
                responseBody.close();
            }
        }
    }
}
