/*******************************************************************************
 *    BDD-Security, application security testing framework
 * 
 * Copyright (C) `2012 Stephen de Vries`
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see `<http://www.gnu.org/licenses/>`.
 ******************************************************************************/
package net.continuumsecurity.web.drivers;

import java.net.UnknownHostException;
import java.util.List;

import net.continuumsecurity.BMProxy;
import net.continuumsecurity.InterceptingProxy;
import net.lightbody.bmp.core.har.HarEntry;
import net.lightbody.bmp.core.har.HarRequest;
import net.lightbody.bmp.core.har.HarResponse;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

public class ProxyFirefoxDriver extends FirefoxDriverFacade implements
		ProxyDriver  {
	BMProxy proxy;
	public final static int proxyPort = 9197;

	public ProxyFirefoxDriver() throws Exception {

		log.debug("Constructing ProxyFirefoxDriver");
		proxy = new BMProxy(proxyPort);
		proxy.start();
		Proxy proxyDef = proxy.seleniumProxy();
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(CapabilityType.PROXY, proxyDef);
		ffDriver = new FirefoxDriver(capabilities);
		// DesiredCapabilities capabilities = new DesiredCapabilities();
		// capabilities.setCapability(CapabilityType.PROXY, getBurpProxy());
		// FirefoxProfile ffProfile = new FirefoxProfile();
		// ffProfile.setPreference("permissions.default.image", 2);
		// ffProfile.setProxyPreferences(getInterceptingProxy());
	}


	@Override
	public void stop() throws Exception {
		proxy.stop();
	}

	@Override
	public void clear() {
		proxy.clear();
	}

	@Override
	public void newLabel(String label) {
		proxy.newLabel(label);
	}

	@Override
	public List<HarEntry> getHistory() {
		return proxy.getHistory();
	}

	@Override
	public List<HarEntry> findInRequestHistory(String regex) {
		return proxy.findInRequestHistory(regex);
	}

	@Override
	public List<HarEntry> findInResponseHistory(String regex) {
		return proxy.findInRequestHistory(regex);
	}

	@Override
	public HarResponse makeRequest(HarRequest request) throws Exception {
		return proxy.makeRequest(request);
	}

	@Override
	public Proxy seleniumProxy() throws UnknownHostException {
		return proxy.seleniumProxy();
	}

	/*
	 * public Proxy getInterceptingProxy() { Proxy proxy = new Proxy();
	 * proxy.setProxyType(Proxy.ProxyType.MANUAL); String proxyStr =
	 * String.format("%s:%d", Config.getProxyHost(),Config.getProxyPort());
	 * proxy.setHttpProxy(proxyStr); proxy.setSslProxy(proxyStr);
	 * 
	 * return proxy; }
	 */

}
