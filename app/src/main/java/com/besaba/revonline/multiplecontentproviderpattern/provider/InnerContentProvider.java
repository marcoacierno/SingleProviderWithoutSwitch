package com.besaba.revonline.multiplecontentproviderpattern.provider;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import java.util.Map;

public interface InnerContentProvider {
  /**
   * The name of the provider. It will be used to generate the URI.
   * For example, if you return "books" your uris will start at
   * books/
   */
  String getProviderName();

  /**
   * <p>Use a map implementation that keep insertion-order! Otherwise
   * the UriMatcher could fail to match your uris.</p>
   * 
   * @returns a Map which contains the uris of the provider and the ids assigned to them
   */
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
