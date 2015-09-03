package com.besaba.revonline.multiplecontentproviderpattern.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import com.besaba.revonline.multiplecontentproviderpattern.provider.sample.BlocksProvider;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseContentProvider extends ContentProvider {
  public static final String AUTHORITY = "com.besaba.revonline.multiplecontentproviderpattern.provider";
  public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
  public static final int MAX_ENDPOINTS_FOR_PROVIDER = 100;

  private static final Map<Integer, InnerContentProvider> providers;
  private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

  static {
    final List<InnerContentProvider> tempProviders = Arrays.<InnerContentProvider>asList(
      new BlocksProvider()
    );

    final Map<Integer, InnerContentProvider> tempMap = new HashMap<>();

    int baseId = 100;

    for (final InnerContentProvider provider : tempProviders) {
      for (final Map.Entry<String, Integer> entry : provider.getUris().entrySet()) {
        final String realPath = provider.getProviderName() + "/" + entry.getKey();
        final int code = baseId + entry.getValue();

        uriMatcher.addURI(
            AUTHORITY,
            realPath,
            code
        );

        tempMap.put(code, provider);
      }

      baseId += MAX_ENDPOINTS_FOR_PROVIDER;
    }

    providers = Collections.unmodifiableMap(tempMap);
  }

  @Override
  public boolean onCreate() {
    return true;
  }

  @Override
  public Cursor query(final Uri uri, final String[] projection, final String selection, final String[] selectionArgs, final String sortOrder) {
    final int match = matchOrThrow(uri);

    final InnerContentProvider provider = providers.get(match);
    final int localId = match % MAX_ENDPOINTS_FOR_PROVIDER;

    return provider.query(localId, projection, selection, selectionArgs, sortOrder);
  }

  @Override
  public String getType(final Uri uri) {
    final int match = matchOrThrow(uri);

    final InnerContentProvider provider = providers.get(match);
    final int localId = match % MAX_ENDPOINTS_FOR_PROVIDER;

    return provider.getType(localId);
  }

  @Override
  public Uri insert(final Uri uri, final ContentValues values) {
    final int match = matchOrThrow(uri);

    final InnerContentProvider provider = providers.get(match);
    final int localId = match % MAX_ENDPOINTS_FOR_PROVIDER;

    return provider.insert(localId, values);
  }

  @Override
  public int delete(final Uri uri, final String selection, final String[] selectionArgs) {
    final int match = matchOrThrow(uri);

    final InnerContentProvider provider = providers.get(match);
    final int localId = match % MAX_ENDPOINTS_FOR_PROVIDER;

    return provider.delete(localId, selection, selectionArgs);
  }

  @Override
  public int update(final Uri uri, final ContentValues values, final String selection, final String[] selectionArgs) {
    final int match = matchOrThrow(uri);

    final InnerContentProvider provider = providers.get(match);
    final int localId = match % MAX_ENDPOINTS_FOR_PROVIDER;

    return provider.update(localId, values, selection, selectionArgs);
  }

  private int matchOrThrow(final Uri uri) {
    final int match = uriMatcher.match(uri);

    if (match == UriMatcher.NO_MATCH) {
      throw new UnsupportedOperationException("Unknown uri: " + uri);
    }

    return match;
  }

  public static Uri buildUriWithId(final InnerContentProvider provider, final String id) {
    return BASE_CONTENT_URI
        .buildUpon()
        .appendPath(provider.getProviderName())
        .appendPath(id)
        .build();
  }
}
