package com.supermarket.gosn;

import com.google.gson.GsonBuilder;
import org.springframework.http.converter.json.GsonHttpMessageConverter;

public class LumaGsonHttpMessageConverter extends GsonHttpMessageConverter {

	public LumaGsonHttpMessageConverter() {
		super.setGson(new GsonBuilder()
				.registerTypeAdapter(ErrorCode.class, new GsonEnumTypeAdapter<>(ErrorCode.SUCCESS))
				.serializeNulls()
				.setDateFormat("yyyy-MM-dd HH:mm:ss")
				.create());
	}
}
