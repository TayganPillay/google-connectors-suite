/**
 * Mule Google Api Commons
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */


package com.google.gdata.client.contacts;

import com.google.gdata.client.AuthTokenFactory;
import com.google.gdata.client.GoogleService;
import com.google.gdata.client.Service;
import com.google.gdata.data.batch.BatchUtils;
import com.google.gdata.data.contacts.ContactFeed;
import com.google.gdata.data.contacts.ContactGroupFeed;
import com.google.gdata.data.contacts.ProfileFeed;
import com.google.gdata.util.Version;
import com.google.gdata.util.VersionRegistry;

/**
 * Extends the basic {@link GoogleService} abstraction to define a service that
 * is preconfigured for access to the Google Contacts data API.
 *
 * 
 */
public class ContactsService extends GoogleService {

  /**
   * The abbreviated name of Google Contacts recognized by Google.  The service
   * name is used when requesting an authentication token.
   */
  public static final String CONTACTS_SERVICE = "cp";

  /**
   * The version ID of the service.
   */
  public static final String CONTACTS_SERVICE_VERSION = "GContacts-Java/" +
      ContactsService.class.getPackage().getImplementationVersion();

  /**
   * GData versions supported by Google Contacts Service.
   */
  public static final class Versions {

    /** Version 1 of the Contacts Data API. */
    public static final Version V1 = new Version(ContactsService.class, "1.0",
        Service.Versions.V1);

    /** Version 2 of the Contacts Data API. */
    public static final Version V2 = new Version(ContactsService.class, "2.0",
        Service.Versions.V2);

    /** Version 3 of the Contacts Data API. */
    public static final Version V3 = new Version(ContactsService.class, "3.0",
        Service.Versions.V2);

    /** Version 3 of the Contacts Data API. */
    public static final Version V3_1 = new Version(ContactsService.class, "3.1",
        Service.Versions.V2);

    private Versions() {}
  }

  /**
   * Default GData version used by the Google Contacts service.
   */
  public static final Version DEFAULT_VERSION =
      Service.initServiceVersion(ContactsService.class, Versions.V3);

  /**
   * Constructs an instance connecting to the Google Contacts service for an
   * application with the name {@code applicationName}.
   *
   * @param applicationName the name of the client application accessing the
   *     service. Application names should preferably have the format
   *     [company-id]-[app-name]-[app-version]. The name will be used by the
   *     Google servers to monitor the source of authentication.
   */
  public ContactsService(String applicationName) {
    super(CONTACTS_SERVICE, applicationName);
    declareExtensions();
  }

  /**
   * Constructs an instance connecting to the Google Contacts service for an
   * application with the name {@code applicationName} and the given {@code
   * GDataRequestFactory} and {@code AuthTokenFactory}. Use this constructor to
   * override the default factories.
   *
   * @param applicationName the name of the client application accessing the
   *     service. Application names should preferably have the format
   *     [company-id]-[app-name]-[app-version]. The name will be used by the
   *     Google servers to monitor the source of authentication.
   * @param requestFactory the request factory that generates gdata request
   *     objects
   * @param authTokenFactory the factory that creates auth tokens
   */
  public ContactsService(String applicationName,
      Service.GDataRequestFactory requestFactory,
      AuthTokenFactory authTokenFactory) {
    super(applicationName, requestFactory, authTokenFactory);
    declareExtensions();
  }

  /**
   * Constructs an instance connecting to the Google Contacts service with name
   * {@code serviceName} for an application with the name {@code
   * applicationName}.  The service will authenticate at the provided {@code
   * domainName}.
   *
   * @param applicationName the name of the client application accessing the
   *     service. Application names should preferably have the format
   *     [company-id]-[app-name]-[app-version]. The name will be used by the
   *     Google servers to monitor the source of authentication.
   * @param protocol        name of protocol to use for authentication
   *     ("http"/"https")
   * @param domainName      the name of the domain hosting the login handler
   */
  public ContactsService(String applicationName, String protocol,
      String domainName) {
    super(CONTACTS_SERVICE, applicationName, protocol, domainName);
    declareExtensions();
  }

  @Override
  public String getServiceVersion() {
    return CONTACTS_SERVICE_VERSION + " " + super.getServiceVersion();
  }

  /**
   * Returns the current GData version used by the Google Contacts service.
   */
  public static Version getVersion() {
    return VersionRegistry.get().getVersion(ContactsService.class);
  }

  /**
   * Declare the extensions of the feeds for the Google Contacts service.
   */
  private void declareExtensions() {
    new ContactFeed().declareExtensions(extProfile);
    new ContactGroupFeed().declareExtensions(extProfile);
    new ProfileFeed().declareExtensions(extProfile);
    BatchUtils.declareExtensions(extProfile);
  }

}
