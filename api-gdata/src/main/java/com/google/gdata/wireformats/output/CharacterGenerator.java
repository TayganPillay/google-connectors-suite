/**
 * Mule Google Api Commons
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */


package com.google.gdata.wireformats.output;

import com.google.gdata.client.GDataProtocol;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * The CharacterGenerator class is a base class to support the implementation of
 * character-oriented {@link OutputGenerator} types.   It provides common logic
 * for determining the appropriate character set encoding based upon output
 * properties and will provide a bridge between output streams and writers.
 * 
 * 
 * 
 * @param <S> source object type for object generation
 */
public abstract class CharacterGenerator<S> implements OutputGenerator<S> {

  /**
   * Returns the character set encoding that should be used for generated
   * output.
   * @param outProps output properties for generated output
   * @return character set encoding for output
   */
  protected static String getCharsetEncoding(OutputProperties outProps) {

    String charset = null;
    if (outProps.getContentType() != null) {
      charset = outProps.getContentType().getCharset();
    }

    if (charset == null) {
      charset = "utf-8"; // default encoding
    }
    return charset;
  }

  /**
   * Returns a {@link Writer} that is properly configured to generate output
   * based upon the request and response attributes.
   * @param outProps output properties for the generated output.
   * @param contentStream
   * @return output writer.
   */
  protected Writer getContentWriter(OutputProperties outProps,
      OutputStream contentStream) throws IOException {
    String encoding = getCharsetEncoding(outProps);
    return new OutputStreamWriter(contentStream, encoding);
  }

  public void generate(OutputStream contentStream, OutputProperties outProps, 
      S s) throws IOException {
    Writer contentWriter = getContentWriter(outProps, contentStream);
    generate(contentWriter, outProps, s);
  }

  /**
   * Generates character content to the specified writer.
   * 
   * @param contentWriter output writer.
   * @param outProps output properties for the generated output.
   * @param s source object for the generated output.
   * @throws IOException
   */
  abstract public void generate(Writer contentWriter, OutputProperties outProps,
      S s) throws IOException;

  /**
   * Returns {@code true} if the output should use a pretty printed format on
   * output.  The default implementation will pretty print output if the 
   * {@link GDataProtocol.Parameter#PRETTYPRINT} parameter has a value of
   * "true".
   * 
   * @param outProps output properties for the generated output.
   * @return {@code true} if the output should be formatted.
   */
  protected boolean usePrettyPrint(OutputProperties outProps) {
    return Boolean.parseBoolean(
        outProps.getQueryParameter(GDataProtocol.Parameter.PRETTYPRINT));
  }
}
