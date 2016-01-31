package com.dmacan.input.util;

import com.dmacan.input.Channel;
import com.dmacan.input.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Mock {

    public static List<Channel> wearCollection() {
        Channel heartRate = new Channel(Channel.Id.HEART_RATE);
        heartRate.setName("Heart rate");
        heartRate.setImage(R.drawable.heart_rate);
        heartRate.setJoinable(true);
        heartRate.setDesc("#heart #life #medical");
        return Collections.singletonList(heartRate);
    }


    public static List<Channel> allCollections() {
        Channel heartRate = new Channel(Channel.Id.HEART_RATE);
        heartRate.setName("Heart rate");
        heartRate.setImage(R.drawable.heart_rate);
        heartRate.setDesc("#heart #life #medical");
        Channel fridge = new Channel(Channel.Id.FRIDGE);
        fridge.setName("Fridge");
        fridge.setImage(R.drawable.fridge);
        fridge.setDesc("#fridge #food");
        Channel motion = new Channel(Channel.Id.MOTION);
        motion.setName("Jury motion");
        motion.setImage(R.drawable.movement);
        motion.setDesc("#movement #cambridge");
        Channel location = new Channel(Channel.Id.LOCATION);
        location.setName("People's locaton");
        location.setImage(R.drawable.location);
        location.setDesc("#location #tourist");
        Channel light = new Channel(Channel.Id.LIGHT);
        light.setName("Average light");
        light.setImage(R.drawable.light);
        light.setDesc("#light #android");
        Channel wifiDisatisfaction = new Channel(Channel.Id.WIFI_FRUSTRATION);
        wifiDisatisfaction.setName("Wifi Disatisfaction");
        wifiDisatisfaction.setImage(R.drawable.sadface);
        wifiDisatisfaction.setDesc("#unhappy #slowconnection #dying");
        List<Channel> channels = new ArrayList<>();
        channels.add(heartRate);
        channels.add(fridge);
        channels.add(motion);
        channels.add(location);
        channels.add(light);
        channels.add(wifiDisatisfaction);
        return channels;
    }


}
