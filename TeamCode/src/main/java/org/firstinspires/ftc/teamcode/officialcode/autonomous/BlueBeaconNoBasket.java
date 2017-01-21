package org.firstinspires.ftc.teamcode.officialcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Created by Higgs Bosons on 1/21/2017.
 */
@Autonomous(name = "BlueNoBasket", group = "Beacon")
public class BlueBeaconNoBasket extends BlueBeacon {
    public BlueBeaconNoBasket(){
        super();
    }

    @Override
    protected boolean isSkipBasket() {
        return true;
    }
}
