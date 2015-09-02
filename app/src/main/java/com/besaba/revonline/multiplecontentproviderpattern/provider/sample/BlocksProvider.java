package com.besaba.revonline.multiplecontentproviderpattern.provider.sample;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;

import com.besaba.revonline.multiplecontentproviderpattern.provider.InnerContentProvider;

import java.util.HashMap;
import java.util.Map;

public class BlocksProvider implements InnerContentProvider {
  private static final int BLOCKS = 0;
  private static final int BLOCKS_BETWEEN = 1;
  private static final int BLOCKS_ID = 2;

  @Override
  public String getProviderName() {
    return "blocks";
  }

  @Override
  public Map<String, Integer> getUris() {
    final Map<String, Integer> map = new HashMap<>(3);

    map.put("", BLOCKS);
    map.put("between/*/*", BLOCKS_BETWEEN);
    map.put("*", BLOCKS_ID);

    return map;
  }

  @Override
  public Cursor query(final int matchId, final String[] projection, final String selection, final String[] selectionArgs, final String sortOrder) {
    switch (matchId) {
      case BLOCKS: {
        final MatrixCursor matrixCursor = new MatrixCursor(new String[]{"_id", "blockName"});
        matrixCursor.addRow(new Object[] {1, "My"});

        return matrixCursor;
      }
      case BLOCKS_BETWEEN: {
        final MatrixCursor matrixCursor = new MatrixCursor(new String[]{"_id", "blockName"});

        matrixCursor.addRow(new Object[] {1, "My"});
        matrixCursor.addRow(new Object[] {2, "Block"});
        matrixCursor.addRow(new Object[] {3, "Between"});

        return matrixCursor;
      }
      case BLOCKS_ID: {
        final MatrixCursor matrixCursor = new MatrixCursor(new String[]{"_id", "blockName"});

        matrixCursor.addRow(new Object[] {1, "Oh, hello boy!"});

        return matrixCursor;
      }
    }

    return null;
  }

  @Override
  public Uri insert(final int matchId, final ContentValues values) {
    return null;
  }

  @Override
  public int delete(final int matchId, final String selection, final String[] selectionArgs) {
    return 0;
  }

  @Override
  public int update(final int matchId, final ContentValues values, final String selection, final String[] selectionArgs) {
    return 0;
  }

  @Override
  public String getType(final int matchId) {
    switch (matchId) {
      case BLOCKS_BETWEEN:
      case BLOCKS: {
        return "vnd.android.cursor.dir/vnd.iosched2014.tag";
      }
      case BLOCKS_ID: {
        return "vnd.android.cursor.item/vnd.iosched2014.tag";
      }
    }

    return null;
  }
}
