package com.chw.pktest.slice;

import com.chw.pktest.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Text;
import ohos.global.config.ConfigType;
import ohos.global.resource.*;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

import java.io.IOException;
import java.util.Arrays;

public class MainAbility2Slice extends AbilitySlice {
    private static final HiLogLabel LABEL_LOG = new HiLogLabel(HiLog.DEBUG, 0xD000F00, MainAbility2Slice.class.getSimpleName());
    private static final String RAW_FILE = "entry/resources/rawfile/example.js";
    private Text resourcesText;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main2);

        resourcesText = (Text) findComponentById(ResourceTable.Id_text_helloworld);

        int string_login = ResourceTable.String_login;
        try {
            String ss1 = getResourceManager().getElement(string_login).getString();
            int string_id_text_font_family_regular =
                    ohos.global.systemres.ResourceTable.String_id_text_font_family_regular;

            RawFileEntry rawFileEntry = getResourceManager().getRawFileEntry("resources/rawfile/example.js");

            int media_ic_app = ohos.global.systemres.ResourceTable.Media_ic_app;
            int string_request_location_reminder_title = ohos.global.systemres.ResourceTable.String_request_location_reminder_title;

            int colorMode = getResourceManager().getConfiguration().colorMode;
            getResourceManager().getConfigManager().getBoolean(ConfigType.CAMERA_SOUND);

            String string = getResourceManager().getElement(ResourceTable.String_dialog_login_success_tip).getString();

        } catch (Exception e) {
            e.printStackTrace();
        }

        HiLog.error(LABEL_LOG, "onStart");
    }

    @Override
    public void onActive() {
        super.onActive();
        try {
            Element elementColor = getResourceManager().getElement(ResourceTable.Color_primary_color);
            String colors = Integer.toHexString(elementColor.getColor());
            HiLog.info(LABEL_LOG, "%{public}s", colors);
            Element elementFloat = getResourceManager().getElement(ResourceTable.Float_margin);
            float floats = elementFloat.getFloat();
            HiLog.info(LABEL_LOG, "%{public}s", String.valueOf(floats));
            Element elementIntarray = getResourceManager().getElement(ResourceTable.Intarray_intarray_1);
            String intArray = Arrays.toString(elementIntarray.getIntArray());
            HiLog.info(LABEL_LOG, "%{public}s", intArray);
            Element elementInteger = getResourceManager().getElement(ResourceTable.Integer_integer_1);
            int integer = elementInteger.getInteger();
            HiLog.info(LABEL_LOG, "%{public}s", String.valueOf(integer));
            Element elementPlural = getResourceManager().getElement(ResourceTable.Plural_eat_apple);
            String pluralString = Arrays.toString(elementPlural.getStringArray());
            HiLog.info(LABEL_LOG, "%{public}s", pluralString);
            Element elementStrarray = getResourceManager().getElement(ResourceTable.Strarray_size);
            String stringArray = Arrays.toString(elementStrarray.getStringArray());
            HiLog.info(LABEL_LOG, "%{public}s", stringArray);
            RawFileEntry rawFileEntry = getResourceManager().getRawFileEntry(RAW_FILE);
            StringBuilder builder = new StringBuilder();
            Resource resource = rawFileEntry.openRawFile();
            byte[] bytes = new byte[resource.available()];
            String appends = null;
            while ((resource.read(bytes)) != -1) {
                appends = (builder.append(new String(bytes, "UTF-8"))).toString();
            }
            HiLog.info(LABEL_LOG, "%{public}s", appends);
            resourcesText.setText(
                    "colors :" + colors + System.lineSeparator()
                            + "floats :" + floats + System.lineSeparator() + "intArray :" + intArray + System.lineSeparator()
                            + "integer :" + integer + System.lineSeparator() + "plural :" + pluralString
                            + System.lineSeparator() + "stringArray :" + stringArray + System.lineSeparator() + "textfile :"
                            + appends);
        } catch (IOException | NotExistException | WrongTypeException e) {
            HiLog.error(LABEL_LOG, "%{public}s", "File exception");
        }
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }
}
