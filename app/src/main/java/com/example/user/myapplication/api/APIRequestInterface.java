package com.example.user.myapplication.api;


import com.example.user.myapplication.common.PlanetXSDKException;
import com.example.user.myapplication.common.RequestBundle;
import com.example.user.myapplication.common.RequestListener;
import com.example.user.myapplication.common.ResponseMessage;

/***
 * 
 * @author lhjung
 * @since 2012.05.25
 * 
 */
interface APIRequestInterface {

	public ResponseMessage request(RequestBundle bundle) throws PlanetXSDKException;

	public ResponseMessage request(RequestBundle bundle, HttpMethod httpMethod) throws PlanetXSDKException;
	
	public ResponseMessage request(RequestBundle bundle, String url, HttpMethod httpMethod) throws PlanetXSDKException;

	public void request(RequestBundle bundle, RequestListener requestListener) throws PlanetXSDKException ;
	public void request(RequestBundle bundle, HttpMethod httpMethod, RequestListener requestListener) throws PlanetXSDKException ;
	public void request(RequestBundle bundle, String url, HttpMethod httpMethod, RequestListener requestListener) throws PlanetXSDKException ;

	
}
