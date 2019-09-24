/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package androidx.test.espresso.action;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.is;

import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.util.Pair;
import android.view.InputDevice;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import androidx.test.espresso.PerformException;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewAssertion;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.annotation.Nonnull;
import org.hamcrest.Matcher;

/** A collection of common {@link ViewActions}. */
public final class ViewActions {

  private ViewActions() {}

  /**
   * The distance of a swipe's start position from the view's edge, in terms of the view's length.
   * We do not start the swipe exactly on the view's edge, but somewhat more inward, since swiping
   * from the exact edge may behave in an unexpected way (e.g. may open a navigation drawer).
   */
  private static final float EDGE_FUZZ_FACTOR = 0.083f;

  /** A set of {@code ViewAssertion}s to be executed before the ViewActions in this class. */
  private static Set<Pair<String, ViewAssertion>> globalAssertions =
      new CopyOnWriteArraySet<Pair<String, ViewAssertion>>();

  /**
   * Adds a {@code ViewAssertion} to be run every time a {@code ViewAction} in this class is
   * performed. The assertion will be run prior to perf