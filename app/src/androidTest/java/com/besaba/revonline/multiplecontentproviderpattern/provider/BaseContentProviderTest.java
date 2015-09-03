package com.besaba.revonline.multiplecontentproviderpattern.provider;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.test.ProviderTestCase2;
import android.test.mock.MockContentResolver;

@TargetApi(Build.VERSION_CODES.KITKAT)
public class BaseContentProviderTest extends ProviderTestCase2<BaseContentProvider> {
  private MockContentResolver mockContentResolver;

  private final static String PROVIDER_AUTHORITY = "com.besaba.revonline.multiplecontentproviderpattern.provider";

  public BaseContentProviderTest() {
    super(BaseContentProvider.class, PROVIDER_AUTHORITY);
  }

  @Override
  protected void setUp() throws Exception {
    super.setUp();

    mockContentResolver = getMockContentResolver();
  }

  public void testTryToReadAValueFromBlockProvider() throws Exception {
    final Uri uri = Uri.parse("content://" + PROVIDER_AUTHORITY + "/blocks");

    final Cursor query = mockContentResolver.query(uri, null, null, null, null);

    assertEquals(1, query.getCount());

    query.moveToNext();

    assertEquals(1, query.getInt(query.getColumnIndex("_id")));
    assertEquals("My", query.getString(query.getColumnIndex("blockName")));
  }

  public void testTryToReadValuesFromBetweenEndpoint() throws Exception {
    final Uri uri = Uri.parse("content://" + PROVIDER_AUTHORITY + "/blocks/between/5/10");
    final Cursor query = mockContentResolver.query(uri, null, null, null, null);

    assertEquals(3, query.getCount());

    query.moveToPosition(0);

    assertEquals(1, query.getInt(query.getColumnIndex("_id")));
    assertEquals("My", query.getString(query.getColumnIndex("blockName")));

    query.moveToPosition(1);

    assertEquals(2, query.getInt(query.getColumnIndex("_id")));
    assertEquals("Block", query.getString(query.getColumnIndex("blockName")));

    query.moveToPosition(2);

    assertEquals(3, query.getInt(query.getColumnIndex("_id")));
    assertEquals("Between", query.getString(query.getColumnIndex("blockName")));
  }

  public void testInsertNewRowInBlocksEndpoint() throws Exception {
    final Uri uri = Uri.parse("content://" + PROVIDER_AUTHORITY + "/blocks/");
    final int elementsBefore = mockContentResolver.query(uri, null, null, null, null, null).getCount();

    final ContentValues values = new ContentValues();
    values.put("blockName", "my block");
    values.put("_id", 5);

    final Uri result = mockContentResolver.insert(uri, values);
    assertEquals(
        "content://com.besaba.revonline.multiplecontentproviderpattern.provider/blocks/3",
        result.toString()
    );
  }
}