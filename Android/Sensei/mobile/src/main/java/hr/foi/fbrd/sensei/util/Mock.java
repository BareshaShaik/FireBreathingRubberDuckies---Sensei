package hr.foi.fbrd.sensei.util;

import android.hardware.Sensor;

import com.dmacan.input.entity.Event;
import com.dmacan.input.entity.ManualCondition;
import com.dmacan.input.entity.TimeCondition;
import com.dmacan.input.factory.InputFactory;
import com.dmacan.input.util.SensorUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class Mock {


    public static List<Event> mockEvents() {
        List<Event> events = new ArrayList<>();
        Event heartRate = new Event();
        heartRate.setActive(true);
        heartRate.setId(UUID.randomUUID().toString());
        heartRate.setName("Heart rate monitor");
        heartRate.setPrice(0);
        heartRate.setSendPush(true);
        heartRate.setInputs(Arrays.asList(InputFactory.createHeartRateInput()));
        heartRate.setConditions(Arrays.asList(
                new ManualCondition()
        ));

        Event lightSensor = new Event();
        lightSensor.setActive(true);
        lightSensor.setId(UUID.randomUUID().toString());
        lightSensor.setName("Room gets too bright");
        lightSensor.setPrice(10);
        lightSensor.setSendPush(false);
        lightSensor.setInputs(Arrays.asList(InputFactory.createInput(SensorUtil.getSensor(Sensor.TYPE_LIGHT))));
        lightSensor.setConditions(Arrays.asList(
                new ManualCondition(),
                new TimeCondition(1)
        ));
        events.add(heartRate);
        events.add(lightSensor);
        return events;
    }

}
