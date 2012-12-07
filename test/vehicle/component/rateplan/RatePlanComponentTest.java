package vehicle.component.rateplan;

import junit.framework.JUnit4TestAdapter;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import vehicle.component.rentalagreement.RentalAgreementComponentTest;
import vehicle.component.vehicle.VehicleComponentTest;
import vehicle.component.vehicletype.VehicleTypeComponent;
import vehicle.component.vehicletype.VehicleTypeComponentTest;
import vehicle.configuration.CarRentalBeanFactory;
import vehicle.domain.RatePlan;
import vehicle.domain.RatePlanTest;
import vehicle.domain.RateSchedule;
import vehicle.domain.RentalAgreement;
import vehicle.domain.ValidState;
import vehicle.domain.VehicleType;
import vehicle.exception.ObjectDoesNotExist;
import vehicle.exception.ObjectExists;
import vehicle.exception.ObjectInIncorrectState;
import vehicle.exception.ObjectInUse;
import vehicle.type.AbstractField;
import vehicle.type.Result;
import vehicle.type.ValidationStatus;

public class RatePlanComponentTest extends Assert {
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(RatePlanComponentTest.class);
    }

    private static final int MISSING_YEAR = 2004;
    private static final String MISSING_MODEL = "MISSING_MODEL";
    private static final String MISSING_MAKE = "MISSING_MAKE";
    private static final String MISSING_NAME = "MISSING_NAME";
    private static final String TEST_PLAN_2 = "Test Plan2";
    private static final String TEST_PLAN = "Test Plan";
    private static final float ADD_HOUR = 99f;
    private static final float ADD_DAY = 10f;
    private static final float NEG_FLOAT = -40f;
    private static final String TEST_PLAN_NAME = "Test Plan1";
    private static final String TEST_VEHICLE_TYPE_NAME = "TestVehicleType";
    private static final String TEST_VEHICLE_TYPE_NAME_2 = "TestVehicleType2";
    private static final String MAKE = "make";
    private static final String MODEL = "model";
    private static final int YEAR = 2006;

    private static RatePlan testRatePlan;
    private static boolean testRatePlanInstalled;

    private static VehicleTypeComponent vtComponent;

    public static RatePlanComponent getRatePlanComponent() {
        return CarRentalBeanFactory.getBean(RatePlanComponent.class);
    }

    public static final RatePlan installTestRatePlan() {
        if (!testRatePlanInstalled) {
            final RatePlan ratePlan = generateRatePlan();
            final Result<RatePlan> result = getRatePlanComponent().createRatePlan(ratePlan);
            Assert.assertTrue("Failed to install test rate plan", result.isSuccess());
            testRatePlan = ratePlan;
            testRatePlanInstalled = true;
        }

        return testRatePlan;
    }

    public static final void uninstallTestRatPlan() {
        if (testRatePlanInstalled) {
            final Result<RatePlan> result = getRatePlanComponent().removeRatePlan(
                    testRatePlan.getName().getValue(), testRatePlan.getVehicleType());
            Assert.assertTrue("Failed to uninstall test rate plan", result.isSuccess());
            testRatePlan = null;
            testRatePlanInstalled = false;
        }
    }

    @BeforeClass
    public static void createTestVehicleType() {
        VehicleTypeComponentTest.installTestVehicleType();
        VehicleComponentTest.installTestVehicle();
        vtComponent = CarRentalBeanFactory.getBean(VehicleTypeComponent.class);
        vtComponent.createVehicleType(new VehicleType(TEST_VEHICLE_TYPE_NAME, MAKE, MODEL, YEAR,
                ValidState.valid));
        vtComponent.createVehicleType(new VehicleType(TEST_VEHICLE_TYPE_NAME_2, MAKE, MODEL, YEAR,
                ValidState.valid));
    }

    @AfterClass
    public static void removeTestVehicleType() {
        VehicleComponentTest.uninstallTestVehicle();
        VehicleTypeComponentTest.uninstallTestVehicleType();
        vtComponent.removeVehicleTypeNamed(TEST_VEHICLE_TYPE_NAME);
        vtComponent.removeVehicleTypeNamed(TEST_VEHICLE_TYPE_NAME_2);
    }

    @Test
    public void createRatePlan() {
        final RatePlan ratePlan = instantiatePopulatedRatePlan(TEST_PLAN, TEST_VEHICLE_TYPE_NAME,
                instantiatePouplatedRateSchedule(29, 137.75f, 14, 10, 34));

        createValidateRemove(ratePlan);
    }

    @Test(expected = ObjectDoesNotExist.class)
    public void createRatePlanWithMissingVehicleType() {
        final RatePlan rp = generateRatePlan();
        rp.setVehicleType(new VehicleType(MISSING_NAME, MISSING_MAKE, MISSING_MODEL, MISSING_YEAR));
        getRatePlanComponent().createRatePlan(rp);
    }

    @Test
    public void createRatePlanSameNameDifferentVehicleType() {
        final RatePlan rp1 = instantiatePopulatedRatePlan(TEST_PLAN, TEST_VEHICLE_TYPE_NAME,
                instantiatePouplatedRateSchedule(34, 138, 12, 10, 40));
        final RatePlan rp2 = instantiatePopulatedRatePlan(TEST_PLAN, TEST_VEHICLE_TYPE_NAME_2,
                instantiatePouplatedRateSchedule(34, 138, 12, 10, 40));

        try {
            createAndValidateSuccess(rp1);
            createAndValidateSuccess(rp2);
        } finally {
            removeRatePlan(rp1);
            removeRatePlan(rp2);
        }
    }

    @Test
    public void createRatePlanSameVehicleTypeDifferentName() {
        final RatePlan rp1 = instantiatePopulatedRatePlan(TEST_PLAN_NAME, TEST_VEHICLE_TYPE_NAME,
                instantiatePouplatedRateSchedule(34, 138, 12, 10, 40));
        final RatePlan rp2 = instantiatePopulatedRatePlan(TEST_PLAN_2, TEST_VEHICLE_TYPE_NAME,
                instantiatePouplatedRateSchedule(34, 138, 12, 10, 40));

        try {
            createAndValidateSuccess(rp1);
            createAndValidateSuccess(rp2);
        } finally {
            removeRatePlan(rp1);
            removeRatePlan(rp2);
        }
    }

    @Test(expected = ObjectExists.class)
    public void createRatePlanAlreadyExists() {
        final RatePlan ratePlan = instantiatePopulatedRatePlan(TEST_PLAN_NAME,
                TEST_VEHICLE_TYPE_NAME, instantiatePouplatedRateSchedule(34, 138, 12, 10, 40));
        try {
            createAndValidateSuccess(ratePlan);
            getRatePlanComponent().createRatePlan(ratePlan);
        } finally {
            removeRatePlan(ratePlan);
        }
    }

    @Test
    public void createRatePlanInvalidWeeklyRate() {
        final RatePlan ratePlan = instantiatePopulatedRatePlan(TEST_PLAN_NAME,
                TEST_VEHICLE_TYPE_NAME, instantiatePouplatedRateSchedule(34, -138, 12, 10, 40));
        createAssertNotSuccessAndInvalidRage(ratePlan, ratePlan.getRateSchedule().getWeeklyRate());
    }

    @Test
    public void createRatePlanInvalidDailyRate() {
        final RatePlan ratePlan = instantiatePopulatedRatePlan(TEST_PLAN_NAME,
                TEST_VEHICLE_TYPE_NAME, instantiatePouplatedRateSchedule(-34, 138, 12, 10, 40));
        createAssertNotSuccessAndInvalidRage(ratePlan, ratePlan.getRateSchedule().getDailyRate());
    }

    @Test
    public void createRatePlanInvalidAuthorizedDriver() {
        final RatePlan ratePlan = instantiatePopulatedRatePlan(TEST_PLAN_NAME,
                TEST_VEHICLE_TYPE_NAME, instantiatePouplatedRateSchedule(34, 138, -12, 10, 40));
        createAssertNotSuccessAndInvalidRage(ratePlan, ratePlan.getRateSchedule()
                .getAuthorizedDriver());
    }

    @Test
    public void createRatePlanInvalidAdditionalDay() {
        final RatePlan ratePlan = instantiatePopulatedRatePlan(TEST_PLAN_NAME,
                TEST_VEHICLE_TYPE_NAME, instantiatePouplatedRateSchedule(34, 138, 12, 10, -40));
        createAssertNotSuccessAndInvalidRage(ratePlan, ratePlan.getRateSchedule()
                .getAdditionalDay());
    }

    @Test
    public void createRatePlanInvalidAdditionalHour() {
        final RatePlan ratePlan = instantiatePopulatedRatePlan(TEST_PLAN_NAME,
                TEST_VEHICLE_TYPE_NAME, instantiatePouplatedRateSchedule(34, 138, 12, -10, 40));
        createAssertNotSuccessAndInvalidRage(ratePlan, ratePlan.getRateSchedule()
                .getAdditionalHour());
    }

    @Test
    public void invalidateRatePlan() {
        final RatePlan ratePlan = instantiatePopulatedRatePlan(TEST_PLAN_NAME,
                TEST_VEHICLE_TYPE_NAME, instantiatePouplatedRateSchedule(34, 138, 12, 10, 40));

        try {
            createAndValidateSuccess(ratePlan);
            Result<RatePlan> result = getRatePlanComponent().retrieveRatePlan(TEST_PLAN_NAME,
                    TEST_VEHICLE_TYPE_NAME);
            assertSuccessAndState(result, ValidState.valid);
            final RatePlan updateRp = result.getValue();
            result = getRatePlanComponent().invalidateRatePlan(updateRp);
            assertTrue(result.isSuccess());
            result = getRatePlanComponent()
                    .retrieveRatePlan(TEST_PLAN_NAME, TEST_VEHICLE_TYPE_NAME);
            assertSuccessAndState(result, ValidState.invalid);
        } finally {
            removeRatePlan(ratePlan);
        }
    }

    @Test(expected = ObjectDoesNotExist.class)
    public void invalidateRatePlanNotFound() {
        final RatePlan ratePlan = instantiatePopulatedRatePlan(TEST_PLAN_NAME,
                TEST_VEHICLE_TYPE_NAME, instantiatePouplatedRateSchedule(34, 138, 12, 10, 40));
        getRatePlanComponent().invalidateRatePlan(ratePlan);
    }

    @Test
    public void invalidateRatePlanAlreadyInvalidate() {
        final RatePlan ratePlan = instantiatePopulatedRatePlan(TEST_PLAN_NAME,
                TEST_VEHICLE_TYPE_NAME, instantiatePouplatedRateSchedule(34, 138, 12, 10, 40));
        ratePlan.setValid(ValidState.invalid);
        try {
            createAndValidateSuccess(ratePlan);
            final Result<RatePlan> result = getRatePlanComponent().retrieveRatePlan(TEST_PLAN_NAME,
                    TEST_VEHICLE_TYPE_NAME);
            assertTrue(result.isSuccess());
            getRatePlanComponent().invalidateRatePlan(ratePlan);
        } finally {
            removeRatePlan(ratePlan);
        }
    }

    @Test
    public void removeRatePlan() {
        final RatePlan ratePlan = instantiatePopulatedRatePlan(TEST_PLAN_NAME,
                TEST_VEHICLE_TYPE_NAME, instantiatePouplatedRateSchedule(34, 138, 12, 10, 40));
        createAndValidateSuccess(ratePlan);
        getRatePlanComponent().removeRatePlan(ratePlan.getName().getValue(),
                ratePlan.getVehicleType());
    }

    @Test(expected = ObjectInUse.class)
    public void removeRatePlanInUse() {
        final RentalAgreement ra = RentalAgreementComponentTest.installTestRentalAgreement();
        final RatePlan rp = ra.getRate();

        try {
            getRatePlanComponent().removeRatePlan(rp.getName().getValue(), rp.getVehicleType());
            fail("Expected ObjectInUse exception to be thrown");
        } finally {
            RentalAgreementComponentTest.uninstallTestRentalAgreement(ra.getId());
        }
    }

    @Test(expected = ObjectDoesNotExist.class)
    public void removeRatePlanDoesNotExist() {
        final RatePlan ratePlan = instantiatePopulatedRatePlan("bogus$$$$$",
                TEST_VEHICLE_TYPE_NAME, instantiatePouplatedRateSchedule(29, 137.75f, 14, 10, 34));
        getRatePlanComponent().removeRatePlan(ratePlan.getName().getValue(),
                ratePlan.getVehicleType());
    }

    @Test
    public void updateRatePlan() {
        final RatePlan ratePlan = instantiatePopulatedRatePlan(TEST_PLAN_NAME,
                TEST_VEHICLE_TYPE_NAME, instantiatePouplatedRateSchedule(34, 138, 12, 10, 40));

        try {
            createAndValidateSuccess(ratePlan);
            ratePlan.getRateSchedule().getAdditionalDay().setValue(ADD_DAY);
            ratePlan.getRateSchedule().getAdditionalHour().setValue(ADD_HOUR);

            final Result<RatePlan> result = getRatePlanComponent().updateRatePlan(ratePlan);
            assertTrue(result.isSuccess());
            assertTrue(ratePlan.getRateSchedule().getAdditionalDay().valueEquals(ADD_DAY));
            assertTrue(ratePlan.getRateSchedule().getAdditionalHour().valueEquals(ADD_HOUR));
        } finally {
            removeRatePlan(ratePlan);
        }
    }

    @Test(expected = ObjectInIncorrectState.class)
    public void updateInvalidedRatePlan() {
        final RatePlan ratePlan = instantiatePopulatedRatePlan(TEST_PLAN_NAME,
                TEST_VEHICLE_TYPE_NAME, instantiatePouplatedRateSchedule(34, 138, 12, 10, 40));
        try {
            createAndValidateSuccess(ratePlan);
            ratePlan.setValid(ValidState.invalid);
            Result<RatePlan> result = getRatePlanComponent().updateRatePlan(ratePlan);
            assertTrue(result.isSuccess());
            ratePlan.getRateSchedule().getAdditionalDay().setValue(ADD_DAY);
            ratePlan.getRateSchedule().getAdditionalHour().setValue(ADD_HOUR);
            result = getRatePlanComponent().updateRatePlan(ratePlan);
            assertFalse(result.isSuccess());
        } finally {
            removeRatePlan(ratePlan);
        }
    }

    @Test(expected = ObjectDoesNotExist.class)
    public void updateRatePlanDoesNotExist() {
        final RatePlan ratePlan = instantiatePopulatedRatePlan(TEST_PLAN_NAME,
                TEST_VEHICLE_TYPE_NAME, instantiatePouplatedRateSchedule(34, 138, 12, 10, 40));
        ratePlan.getRateSchedule().getAdditionalDay().setValue(ADD_DAY);
        ratePlan.getRateSchedule().getAdditionalHour().setValue(ADD_HOUR);
        getRatePlanComponent().updateRatePlan(ratePlan);
    }

    @Test
    public void updateRatePlanInvalidWeeklyRate() {
        final RatePlan ratePlan = instantiatePopulatedRatePlan(TEST_PLAN_NAME,
                TEST_VEHICLE_TYPE_NAME, instantiatePouplatedRateSchedule(34, 138, 12, 10, 40));
        try {
            createAndValidateSuccess(ratePlan);
            ratePlan.getRateSchedule().getWeeklyRate().setValue(NEG_FLOAT);
            updateAssertNotSuccessAndInvalidRage(ratePlan, ratePlan.getRateSchedule()
                    .getWeeklyRate());
        } finally {
            removeRatePlan(ratePlan);
        }
    }

    @Test
    public void updateRatePlanInvalidDailyRate() {
        final RatePlan ratePlan = instantiatePopulatedRatePlan(TEST_PLAN_NAME,
                TEST_VEHICLE_TYPE_NAME, instantiatePouplatedRateSchedule(34, 138, 12, 10, 40));
        try {
            createAndValidateSuccess(ratePlan);
            ratePlan.getRateSchedule().getDailyRate().setValue(NEG_FLOAT);
            updateAssertNotSuccessAndInvalidRage(ratePlan, ratePlan.getRateSchedule()
                    .getDailyRate());
        } finally {
            removeRatePlan(ratePlan);
        }
    }

    @Test
    public void updateRatePlanInvalidAuthorizedDriver() {
        final RatePlan ratePlan = instantiatePopulatedRatePlan(TEST_PLAN_NAME,
                TEST_VEHICLE_TYPE_NAME, instantiatePouplatedRateSchedule(34, 138, 12, 10, 40));
        try {
            createAndValidateSuccess(ratePlan);
            ratePlan.getRateSchedule().getAuthorizedDriver().setValue(NEG_FLOAT);
            updateAssertNotSuccessAndInvalidRage(ratePlan, ratePlan.getRateSchedule()
                    .getAuthorizedDriver());
        } finally {
            removeRatePlan(ratePlan);
        }
    }

    @Test
    public void updateRatePlanInvalidAdditionalDay() {
        final RatePlan ratePlan = instantiatePopulatedRatePlan(TEST_PLAN_NAME,
                TEST_VEHICLE_TYPE_NAME, instantiatePouplatedRateSchedule(34, 138, 12, 10, 40));
        try {
            createAndValidateSuccess(ratePlan);
            ratePlan.getRateSchedule().getAdditionalDay().setValue(NEG_FLOAT);
            updateAssertNotSuccessAndInvalidRage(ratePlan, ratePlan.getRateSchedule()
                    .getAdditionalDay());
        } finally {
            removeRatePlan(ratePlan);
        }
    }

    @Test
    public void updateRatePlanInvalidAdditionalHour() {
        final RatePlan ratePlan = instantiatePopulatedRatePlan(TEST_PLAN_NAME,
                TEST_VEHICLE_TYPE_NAME, instantiatePouplatedRateSchedule(34, 138, 12, 10, 40));
        try {
            createAndValidateSuccess(ratePlan);
            ratePlan.getRateSchedule().getAdditionalHour().setValue(NEG_FLOAT);
            updateAssertNotSuccessAndInvalidRage(ratePlan, ratePlan.getRateSchedule()
                    .getAdditionalHour());
        } finally {
            removeRatePlan(ratePlan);
        }
    }

    private static RatePlan generateRatePlan() {
        return RatePlanTest.generateRatePlan();
    }

    private void createAndValidateSuccess(final RatePlan rp) {
        final Result<RatePlan> result = getRatePlanComponent().createRatePlan(rp);
        assertTrue(result.isSuccess());
    }

    private void createValidateRemove(final RatePlan rp) {
        try {
            createAndValidateSuccess(rp);
        } finally {
            final Result<RatePlan> result = getRatePlanComponent().removeRatePlan(
                    rp.getName().getValue(), rp.getVehicleType());
            assertTrue(result.isSuccess());
        }
    }

    private void assertSuccessAndState(final Result<RatePlan> result, final ValidState state) {
        assertTrue("Expected successful results", result.isSuccess());
        assertEquals(state, result.getValue().getValid());
    }

    private static RatePlan instantiateBasicRatePlan(final String name, final String vehicleTypeName) {
        final VehicleType vt = vtComponent.getVehicleTypeNamed(vehicleTypeName).getValue();
        return new RatePlan(name, new RateSchedule(), vt, ValidState.valid);
    }

    private static RateSchedule instantiatePouplatedRateSchedule(final float dailyRate,
            final float weeklyRate, final float authorizedDriver, final float additionalHour,
            final float additionalDay) {
        final RateSchedule rateSchedule = new RateSchedule();
        rateSchedule.getAdditionalDay().setValue(additionalDay);
        rateSchedule.getAdditionalHour().setValue(additionalHour);
        rateSchedule.getAuthorizedDriver().setValue(authorizedDriver);
        rateSchedule.getDailyRate().setValue(dailyRate);
        rateSchedule.getWeeklyRate().setValue(weeklyRate);

        return rateSchedule;
    }

    private static RatePlan instantiatePopulatedRatePlan(final String name,
            final String vehicleTypeName, final RateSchedule schedule) {
        final RatePlan ratePlan = instantiateBasicRatePlan(name, vehicleTypeName);
        ratePlan.setRateSchedule(schedule);
        return ratePlan;
    }

    private void removeRatePlan(final RatePlan ratePlan) {
        getRatePlanComponent().removeRatePlan(ratePlan.getName().getValue(),
                ratePlan.getVehicleType());
    }

    private void assertNotSuccessAndInvalidRange(final AbstractField<?> field,
            final Result<RatePlan> result) {
        assertFalse(result.isSuccess());
        assertTrue(field.validationStatusIs(ValidationStatus.invalidRange));
    }

    private void createAssertNotSuccessAndInvalidRage(final RatePlan ratePlan,
            final AbstractField<?> field) {
        final Result<RatePlan> result = getRatePlanComponent().createRatePlan(ratePlan);
        assertNotSuccessAndInvalidRange(field, result);
    }

    private void updateAssertNotSuccessAndInvalidRage(final RatePlan ratePlan,
            final AbstractField<?> field) {
        final Result<RatePlan> result = getRatePlanComponent().updateRatePlan(ratePlan);
        assertNotSuccessAndInvalidRange(field, result);
    }

}
