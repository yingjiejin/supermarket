package com.supermarket.gosn;

import com.google.gson.JsonObject;

public interface GsonEnum<E> {

	JsonObject serialize();

	E deserialize(String jsonEnum);

}