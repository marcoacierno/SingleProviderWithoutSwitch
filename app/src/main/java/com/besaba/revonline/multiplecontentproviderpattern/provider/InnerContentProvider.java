package com.besaba.revonline.multiplecontentproviderpattern.provider;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import java.util.Map;

public interface InnerContentProvider {
  String getProviderName();

  Map<String, Integer> getUris();

  Cursor query(final int matchId, final String[] projection, final String selection, final String[] selectionArgs, final String sortOrder);
  Uri insert(final int matchId, final ContentValues values);
  int delete(final int matchId, final String selection, final String[] selectionArgs);
  int update(final int matchId, final ContentValues values, final String selection, final String[] selectionArgs);

  /**
   * @param matchId The same value you passed in getUris
   * @return The minetype of the endpoint
   */
  String getType(final int matchId);
}
