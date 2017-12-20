package com.example.android.sunshine.sync;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;

import com.example.android.sunshine.data.WeatherContract;
import com.example.android.sunshine.utilities.NetworkUtils;
import com.example.android.sunshine.utilities.OpenWeatherJsonUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;

//  COMPLETED (1) Create a class called SunshineSyncTask
class SunshineSyncTask {

//  COMPLETED (2) Within SunshineSyncTask, create a synchronized public static void method called syncWeather
    synchronized public static void syncWeather(Context context) {

        try {
            //      COMPLETED (3) Within syncWeather, fetch new weather data
            // Getting weather data url
            URL weatherRequestUrl = NetworkUtils.getUrl(context);

            // Downloading weather data in JSON format
            String jsonWeatherData = NetworkUtils.getResponseFromHttpUrl(weatherRequestUrl);

            // Parsing JSON into ContentValues
            ContentValues[] weatherData = OpenWeatherJsonUtils.getWeatherContentValuesFromJson(context, jsonWeatherData);

            // Refreshing data
            if(weatherData != null && weatherData.length != 0) {
                ContentResolver contentResolver = context.getContentResolver();

                //      COMPLETED (4) If we have valid results, delete the old data and insert the new
                contentResolver.delete(WeatherContract.WeatherEntry.CONTENT_URI, null, null);

                // Fetching new data into Content Resolver
                contentResolver.bulkInsert(WeatherContract.WeatherEntry.CONTENT_URI, weatherData);
            }
        } catch (IOException e) {

        } catch (JSONException e) {

        }
    }
}