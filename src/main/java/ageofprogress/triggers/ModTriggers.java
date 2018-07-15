package ageofprogress.triggers;

public class ModTriggers

{
    //    public static final CustomTrigger ITEMPRED1 = new CustomTrigger("step1");
    public static final CustomTrigger ITEMPRED2 = new CustomTrigger("step2");
    public static final CustomTrigger ITEMPRED2_1 = new CustomTrigger("step2_1");
    public static final CustomTrigger ITEMPRED2_2 = new CustomTrigger("step2_2");
    public static final CustomTrigger ITEMPRED3 = new CustomTrigger("step3");

    /*
     * This array just makes it convenient to register all the criteria.
     */
    public static final CustomTrigger[] TRIGGER_ARRAY = new CustomTrigger[]{
            // ITEMPRED1,
            ITEMPRED2,
            ITEMPRED2_1,
            ITEMPRED2_2,
            ITEMPRED3
    };
}