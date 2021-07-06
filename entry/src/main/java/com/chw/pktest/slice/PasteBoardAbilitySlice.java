package com.chw.pktest.slice;

import com.chw.pktest.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Text;
import ohos.miscservices.pasteboard.PasteData;
import ohos.miscservices.pasteboard.SystemPasteboard;
import ohos.utils.PacMap;

public class PasteBoardAbilitySlice extends AbilitySlice {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_paste_board);

        SystemPasteboard systemPasteboard = SystemPasteboard.getSystemPasteboard(this);
        if (systemPasteboard == null) {
            return;
        }

        systemPasteboard.addPasteDataChangedListener(() -> {

        });
        boolean hasPasteData = systemPasteboard.hasPasteData();

        PasteData pasteData = PasteData.creatPlainTextData("ssss");
        PasteData.Record record = PasteData.Record.createPlainTextRecord("sss");
        pasteData.addRecord(record);

        PasteData.DataProperty property = pasteData.getProperty();
        boolean hasMimeType = property.hasMimeType(PasteData.MIMETYPE_TEXT_PLAIN);
        long timestamp = property.getTimestamp();

        PacMap pacMap1 = new PacMap();
        pacMap1.putIntValue("age", 100);
        property.setAdditions(pacMap1);

        PasteData pasteData1 = systemPasteboard.getPasteData();
        if (pasteData1 == null) {
            return;
        }
        PasteData.DataProperty dataProperty = pasteData.getProperty();
        boolean hasHtml = dataProperty.hasMimeType(PasteData.MIMETYPE_TEXT_HTML);
        boolean hasText = dataProperty.hasMimeType(PasteData.MIMETYPE_TEXT_PLAIN);
        if (hasHtml || hasText) {
            Text text = (Text) findComponentById(ResourceTable.Id_text);
            for (int i = 0; i < pasteData.getRecordCount(); i++) {
                PasteData.Record record1 = pasteData.getRecordAt(i);
                String mimeType = record1.getMimeType();
                if (mimeType.equals(PasteData.MIMETYPE_TEXT_HTML)) {
                    text.setText(record1.getHtmlText());
                    break;
                } else if (mimeType.equals(PasteData.MIMETYPE_TEXT_PLAIN)) {
                    text.setText(record1.getPlainText().toString());
                    break;
                } else {
                    // skip records of other Mime type
                }
            }
        }
    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }
}
