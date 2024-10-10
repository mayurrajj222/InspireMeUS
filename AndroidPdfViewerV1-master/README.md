

# Android PdfViewer

__This is repo with version 1.x of [AndroidPdfViewer](https://github.com/barteksc/AndroidPdfViewer).
Version 1.x uses different engine for drawing document on canvas, so if you don't like 2.x version, try this.
This version is still updated and supported.__

Library for displaying PDF documents on Android, with `animations`, `gestures`, `zoom` and `double tap` support.
It is based on [PdfiumAndroid](https://github.com/barteksc/PdfiumAndroid) for decoding PDF files. Works on API 11 and higher.
Licensed under Apache License 2.0.

## What's new in 1.6.0?
* Add change from AndroidPdfViewer 2.4.0 which replaces RenderingAsyncTask with Handler to simplify code and work with testing frameworks

## Installation

Add to _build.gradle_:

`compile 'com.github.barteksc:android-pdf-viewer:1.6.0'`

Library is available in jcenter repository, probably it'll be in Maven Central soon.

## Include PDFView in your layout

``` xml
<com.github.barteksc.pdfviewer.PDFView
        android:id="@+id/pdfView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>
```

## Load a PDF file

All available options with default values:
``` java
pdfView.fromUri(Uri)
or
pdfView.fromFile(File)
or
pdfView.fromBytes(byte[])
or
pdfView.fromStream(InputStream)
or
pdfView.fromSource(DocumentSource)
or
pdfView.fromAsset(String)
    .pages(0, 2, 1, 3, 3, 3) // all pages are displayed by default
    .enableSwipe(true)
    .enableDoubletap(true)
    .swipeVertical(false)
    .defaultPage(1)
    .showMinimap(false)
    .onDraw(onDrawListener)
    .onLoad(onLoadCompleteListener)
    .onPageChange(onPageChangeListener)
    .onError(onErrorListener)
    .enableAnnotationRendering(false)
    .password(null)
    .showPageWithAnimation(true)
    .load();
```

* `enableSwipe` is optional, it allows you to block changing pages using swipe
* `pages` is optional, it allows you to filter and order the pages of the PDF as you need
* `onDraw` is also optional, and allows you to draw something on a provided canvas, above the current page

## Show scrollbar

Use **ScrollBar** class to place scrollbar view near **PDFView**

1. in layout XML (it's important that the parent view is **RelativeLayout**):

    Vertically:
    ``` xml
    <RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <com.github.barteksc.pdfviewer.PDFView
            android:id="@+id/pdfView"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:layout_toLeftOf="@+id/scrollBar"/>

        <com.github.barteksc.pdfviewer.ScrollBar
            android:id="@+id/scrollBar"
            android:layout_width="wrap_content"
            android:layout_height="match_parent"
            android:layout_alignParentRight="true"
            android:layout_alignParentEnd="true" />

    </RelativeLayout>
    ```
    Horizontally:
    ``` xml
    <RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    	android:layout_width="match_parent"
    	android:layout_height="match_parent"
    	xmlns:app="http://schemas.android.com/apk/res-auto">

        <com.github.barteksc.pdfviewer.PDFView
            android:id="@+id/pdfView"
            android:layout_width="match_parent"
            android:layout_height="match_parent"/>

    	<com.github.barteksc.pdfviewer.ScrollBar
    		android:id="@+id/scrollBar"
    		android:layout_width="match_parent"
    		android:layout_height="wrap_content"
    		app:sb_horizontal="true"
    		android:layout_alignParentBottom="true" />

    </RelativeLayout>
    ```
2. in activity or fragment
    ``` java

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            ...

            PDFView pdfView = (PDFView) findViewById(R.id.pdfView);
            ScrollBar scrollBar = (ScrollBar) findViewById(R.id.scrollBar);
            pdfView.setScrollBar(scrollBar);
        }
    ```

    `scrollBar.setHorizontal(true);` or `app:sb_horizontal="true"` may be used to set **ScrollBar** in horizontal mode.


Scrollbar styling:
``` xml
    <com.github.barteksc.pdfviewpager.view.ScrollBar
        android:layout_width="wrap_content"
        android:layout_height="match_parent"
        app:sb_handlerColor="..." <!-- scrollbar handler color -->
        app:sb_indicatorColor="..." <!-- background color of current page indicator -->
        app:sb_indicatorTextColor="..." <!-- text color of current page indicator -->
        app:sb_horizontal="true|false|reference" <!-- whether to set horizontal mode -->
        android:background="..." <!-- scrollbar background -->
        />
```

**ScrollBarPageIndicator** is added to scrollbar automatically and is shown while dragging scrollbar handler,
 displaying number of page on current position. Its position is automatically calculated based on **ScrollBar**'s position.

## Document sources
Version 1.5.0 introduced _document sources_, which are just providers for PDF documents.
Every provider implements **DocumentSource** interface.
Predefined providers are available in **com.github.barteksc.pdfviewer.source** package and can be used as
samples for creating custom ones.

Predefined providers can be used with shorthand methods:
```
pdfView.fromUri(Uri)
pdfView.fromFile(File)
pdfView.fromBytes(byte[])
pdfView.fromStream(InputStream)
pdfView.fromAsset(String)
```
Custom providers may be used with `pdfView.fromSource(DocumentSource)` method.

## Additional options

### Bitmap quality
By default, generated bitmaps are _compressed_ with `RGB_565` format to reduce memory consumption.
Rendering with `ARGB_8888` can be forced by using `pdfView.useBestQuality(true)` method.

### Double tap zooming
There are three zoom levels: min (default 1), mid (default 1.75) and max (default 3). On first double tap,
view is zoomed to mid level, on second to max level, and on third returns to min level.
If you are between mid and max levels, double tapping causes zooming to max and so on.

Zoom levels can be changed using following methods:

``` java
void setMinZoom(float zoom);
void setMidZoom(float zoom);
void setMaxZoom(float zoom);
```

## Possible questions
### Why resulting apk is so big?
Android PdfViewer depends on PdfiumAndroid, which is set of native libraries (almost 16 MB) for many architectures.
Apk must contain all this libraries to run on every device available on market.
Fortunately, Google Play allows us to upload multiple apks, e.g. one per every architecture.
There is good article on automatically splitting your application into multiple apks,
available [here](http://ph0b.com/android-studio-gradle-and-ndk-integration/).
Most important section is _Improving multiple APKs creation and versionCode handling with APK Splits_, but whole article is worth reading.
You only need to do this in your application, no need for forking PdfiumAndroid or so.

## One more thing
If you have any suggestions on making this lib better, write me, create issue or write some code and send pull request.

## License

Created with the help of android-pdfview by [Joan Zapata](http://joanzapata.com/)
```
Copyright 2016 Bartosz Schiller

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
