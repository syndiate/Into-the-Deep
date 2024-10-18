package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "DriveJava", group = "Auto")
public class DriveJava extends RobotBase {

    private double powerFactor = 1.25;
    private double manualArmSpeed = 0.01;

    @Override
    public void runOpMode() {
        this.initMotors();
        telemetry.addLine("Waiting for start");
        telemetry.update();

        waitForStart();
        double currentArmPosition = 0.35;
        while (opModeIsActive()) {

            moveServo(arm, currentArmPosition, 20);
            lift.setPower(gamepad2.right_stick_y * 0.5);

            if (gamepad2.left_stick_y > 0) {
                currentArmPosition += this.manualArmSpeed; // increase by a small step
                if(currentArmPosition > 1) currentArmPosition = 1;
            } else if (gamepad2.left_stick_y < 0) {
                currentArmPosition -= this.manualArmSpeed; // decrease by a small steps
                if(currentArmPosition < -1) currentArmPosition = -1;
            }

            telemetry.addData("arm sent pos tic: ", currentArmPosition);
            telemetry.addData("arm pos tic actual: ", arm.getPosition());
            telemetry.addData("lift pos tic actual: ", lift.getCurrentPosition());
            telemetry.addData("lift power sent: ", gamepad2.right_stick_y);
            telemetry.addData("lift power actual: ", lift.getPower());
            telemetry.update();

            // grab claw
            if (gamepad2.left_trigger > 0) {
                claw.setPosition(0.18);
            }
            // drop
            if (gamepad2.right_trigger > 0) {
                claw.setPosition(0.06);
            }


            moveBot(-gamepad1.left_stick_y, (gamepad1.right_stick_x), gamepad1.left_stick_x);
//            moveBot(-gamepad1.left_stick_y, (gamepad1.right_stick_x), gamepad1.left_stick_x);
        }
    }


    private void moveBot(float vertical, float pivot, float horizontal) {
        pivot *= 0.6;
        rf_drive.setPower(powerFactor * (-pivot + (vertical - horizontal)));
        rb_drive.setPower(powerFactor * (-pivot + vertical + horizontal));
        lf_drive.setPower(powerFactor * (pivot + vertical + horizontal));
        lb_drive.setPower(powerFactor * (pivot + (vertical - horizontal)));
    }
}
