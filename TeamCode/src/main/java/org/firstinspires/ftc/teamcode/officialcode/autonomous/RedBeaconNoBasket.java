package org.firstinspires.ftc.teamcode.officialcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Created by Higgs Bosons on 1/21/2017.
 */
@Autonomous(name = "RedNoBasket", group = "Beacon")
public class RedBeaconNoBasket extends RedBeacon {
    public RedBeaconNoBasket(){
        super();
    }

    @Override
    protected boolean isSkipBasket() {
        return true;
    }
}
