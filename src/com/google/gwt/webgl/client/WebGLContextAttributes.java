package com.google.gwt.webgl.client;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * The WebGLContextAttributes interface contains drawing surface attributes and
 * is passed as the second parameter to getContext. A native object may be
 * supplied as this parameter; the specified attributes will be queried from
 * this object.
 */
public class WebGLContextAttributes extends JavaScriptObject {

  public static native WebGLContextAttributes create() /*-{
    return {};
  }-*/;

  protected WebGLContextAttributes() {
  }

  /**
   * Default: true. If the value is true, the drawing buffer has an alpha
   * channel for the purposes of performing OpenGL destination alpha operations
   * and compositing with the page. If the value is false, no alpha buffer is
   * available.
   */
  // attribute boolean alpha;

  /**
   * Default: true. If the value is true, the drawing buffer has a depth buffer
   * of at least 16 bits. If the value is false, no depth buffer is available.
   */
  // attribute boolean depth;

  /**
   * Default: false. If the value is true, the drawing buffer has a stencil
   * buffer of at least 8 bits. If the value is false, no stencil buffer is
   * available.
   */
  // attribute boolean stencil;

  /**
   * Default: true. If the value is true and the implementation supports
   * antialiasing the drawing buffer will perform antialiasing using its choice
   * of technique (multisample/supersample) and quality. If the value is false
   * or the implementation does not support antialiasing, no antialiasing is
   * performed.
   */
  // attribute boolean antialias;

  /**
   * Default: true. If the value is true the page compositor will assume the
   * drawing buffer contains colors with premultiplied alpha. If the value is
   * false the page compositor will assume that colors in the drawing buffer are
   * not premultiplied. This flag is ignored if the alpha flag is false. See
   * Premultiplied Alpha for more information on the effects of the
   * premultipliedAlpha flag.
   */
  // attribute boolean premultipliedAlpha;
}
