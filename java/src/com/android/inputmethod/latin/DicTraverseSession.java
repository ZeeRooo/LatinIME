/*
 * Copyright (C) 2012, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.inputmethod.latin;

import java.util.Locale;

public class DicTraverseSession {
    static {
        JniUtils.loadNativeLibrary();
    }
    private native long setDicTraverseSessionNative(String locale);
    private native void initDicTraverseSessionNative(
            long nativeDicTraverseSession, int[] previousWord, int previwousWordLength);
    private native void releaseDicTraverseSessionNative(long nativeDicTraverseSession);

    private long mNativeDicTraverseSession;

    public DicTraverseSession(Locale locale) {
        mNativeDicTraverseSession = createNativeDicTraverseSession(
                locale != null ? locale.toString() : "");
        initSession();
    }

    public long getSession() {
        return mNativeDicTraverseSession;
    }

    public void initSession() {
        initSession(null, 0);
    }

    public void initSession(int[] previousWord, int previousWordLength) {
        initDicTraverseSessionNative(mNativeDicTraverseSession, previousWord, previousWordLength);
    }

    private final long createNativeDicTraverseSession(String locale) {
        return setDicTraverseSessionNative(locale);
    }

    private void closeInternal() {
        if (mNativeDicTraverseSession != 0) {
            releaseDicTraverseSessionNative(mNativeDicTraverseSession);
            mNativeDicTraverseSession = 0;
        }
    }

    public void close() {
        closeInternal();
    }

    @Override
    protected void finalize() throws Throwable {
        try {
            closeInternal();
        } finally {
            super.finalize();
        }
    }
}
