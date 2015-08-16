package com.moogiesoft.stockmanager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.swing.JTextArea;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.paypal.exception.ClientActionRequiredException;
import com.paypal.exception.HttpErrorException;
import com.paypal.exception.InvalidCredentialException;
import com.paypal.exception.InvalidResponseDataException;
import com.paypal.exception.MissingCredentialException;
import com.paypal.exception.SSLConfigurationException;
import com.paypal.sdk.exceptions.OAuthException;

import urn.ebay.api.PayPalAPI.BMButtonSearchReq;
import urn.ebay.api.PayPalAPI.BMButtonSearchRequestType;
import urn.ebay.api.PayPalAPI.BMButtonSearchResponseType;
import urn.ebay.api.PayPalAPI.BMCreateButtonReq;
import urn.ebay.api.PayPalAPI.BMCreateButtonRequestType;
import urn.ebay.api.PayPalAPI.BMCreateButtonResponseType;
import urn.ebay.api.PayPalAPI.BMGetButtonDetailsReq;
import urn.ebay.api.PayPalAPI.BMGetButtonDetailsRequestType;
import urn.ebay.api.PayPalAPI.BMGetButtonDetailsResponseType;
import urn.ebay.api.PayPalAPI.BMGetInventoryReq;
import urn.ebay.api.PayPalAPI.BMGetInventoryRequestType;
import urn.ebay.api.PayPalAPI.BMGetInventoryResponseType;
import urn.ebay.api.PayPalAPI.BMManageButtonStatusReq;
import urn.ebay.api.PayPalAPI.BMManageButtonStatusRequestType;
import urn.ebay.api.PayPalAPI.BMManageButtonStatusResponseType;
import urn.ebay.api.PayPalAPI.BMSetInventoryReq;
import urn.ebay.api.PayPalAPI.BMSetInventoryRequestType;
import urn.ebay.api.PayPalAPI.BMSetInventoryResponseType;
import urn.ebay.api.PayPalAPI.BMUpdateButtonReq;
import urn.ebay.api.PayPalAPI.BMUpdateButtonRequestType;
import urn.ebay.api.PayPalAPI.BMUpdateButtonResponseType;
import urn.ebay.api.PayPalAPI.InstallmentDetailsType;
import urn.ebay.api.PayPalAPI.OptionDetailsType;
import urn.ebay.api.PayPalAPI.OptionSelectionDetailsType;
import urn.ebay.api.PayPalAPI.PayPalAPIInterfaceServiceService;
import urn.ebay.apis.eBLBaseComponents.BillingPeriodType;
import urn.ebay.apis.eBLBaseComponents.ButtonCodeType;
import urn.ebay.apis.eBLBaseComponents.ButtonSearchResultType;
import urn.ebay.apis.eBLBaseComponents.ButtonStatusType;
import urn.ebay.apis.eBLBaseComponents.ButtonTypeType;
import urn.ebay.apis.eBLBaseComponents.ErrorType;
import urn.ebay.apis.eBLBaseComponents.ItemTrackingDetailsType;
import urn.ebay.apis.eBLBaseComponents.OptionTypeListType;

public class PayPalConnector {

	final static String ISO_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
	final static SimpleDateFormat sdf = new SimpleDateFormat(ISO_FORMAT);
	final static TimeZone utc = TimeZone.getTimeZone("UTC");

	static
	{
		sdf.setTimeZone(utc);
	}
	
	PayPalConnector()
	{
		
	}
	
	public List<PayPalButton> getPayPalButtons() throws Exception
	{
		return getPayPalButtons(null,null);
	}
	
	public List<PayPalButton> getPayPalButtons(StockDatabase stockDatabase) throws Exception
	{
		return getPayPalButtons(null,stockDatabase);
	}
	
	public List<PayPalButton> getPayPalButtons(JTextArea textArea,StockDatabase stockDatabase) throws Exception
	{
		List<PayPalButton> result = new ArrayList<PayPalButton>();
//		try
//		{
			
		// Configuration map containing signature credentials and other required configuration.
	
			Map<String,String> configurationMap =  Configuration.getAcctAndConfig();
			
			// Creating service wrapper object to make an API call by loading configuration map. 
			PayPalAPIInterfaceServiceService service = new PayPalAPIInterfaceServiceService(configurationMap);
			
			List<ButtonSearchResultType> buttons = new ArrayList<ButtonSearchResultType>();
			{
				BMButtonSearchReq request = new BMButtonSearchReq();
				BMButtonSearchRequestType reqType = new BMButtonSearchRequestType();

//				Date date = new Date(0);
//				sdf.setTimeZone(utc);
//						System.out.println(sdf.format(date)+"Z");
//				System.out.println("2012-08-24T05:38:48Z");
//						System.out.println(new Date(0).toGMTString());
				
//						reqType.setStartDate(sdf.format(date)+"Z");
				reqType.setStartDate("2012-08-24T05:38:48Z");

				
				request.setBMButtonSearchRequest(reqType);
				
				// ## Making API call
				// Invoke the appropriate method corresponding to API in service
				// wrapper object

				
				BMButtonSearchResponseType resp = service.bMButtonSearch(request);
				if (resp != null) {
					if (resp.getAck().toString().equalsIgnoreCase("SUCCESS")) {
						
						buttons=resp.getButtonSearchResult();
					} else {
						
						StringBuilder sb = new StringBuilder();
						sb.append(resp.getAck().toString()+"\n");
						for (ErrorType error : resp.getErrors())
						{
							sb.append(error.getLongMessage()+"\n");
							System.out.println(error.getLongMessage());
						}
						throw new RuntimeException("unable to communicate to PayPal! - "+sb.toString());
					}
				}
			}
			
			List<ButtonSearchResultType> lazyLoadButtons = new ArrayList<ButtonSearchResultType>();
			List<PayPalButton> lazyLoadResult = new ArrayList<PayPalButton>();
			
			if (stockDatabase==null)
			{
				lazyLoadButtons.addAll(buttons);
			}
			else
			{
				for (ButtonSearchResultType button:buttons)
				{
					StockItem item = stockDatabase.getItem(button.getHostedButtonID());
					if (item==null)
					{
						lazyLoadButtons.add(button);
					}
					else 
					{
						Date date  = sdf.parse(button.getModifyDate().replace("Z", ""));
						if (date.compareTo(item.getModifiedDate())>0)
						{
							lazyLoadButtons.add(button);
						}
						else
						{
							PayPalButton payPalButton = new PayPalButton(item);
							payPalButton.setModifiedDate(date);
							result.add(payPalButton);
						}
					}
				}
			}
			
			if (textArea!=null) textArea.append("Downloading:");
			int count=0;
			
			for (ButtonSearchResultType button:lazyLoadButtons)
			{
				if (textArea!=null) 
				{
					String text = textArea.getText();
					text=text.substring(0, text.indexOf("Downloading:")+"Downloading:".length());
					text+=" "+(((double) 100*count++)/(2*lazyLoadButtons.size()))+"%";
					textArea.setText(text);
				}
				BMGetButtonDetailsReq request = new BMGetButtonDetailsReq();
				BMGetButtonDetailsRequestType reqType = new BMGetButtonDetailsRequestType();
				
				//(Required) The ID of the hosted button whose details you want to obtain.
				reqType.setHostedButtonID(button.getHostedButtonID());

				request.setBMGetButtonDetailsRequest(reqType);
				
				// ## Making API call
				// Invoke the appropriate method corresponding to API in service
				// wrapper object
				BMGetButtonDetailsResponseType resp = service.bMGetButtonDetails(request);
				if (resp != null) {
					if (resp.getAck().toString().equalsIgnoreCase("SUCCESS")) {
						
//								resp.getButtonVar().forEach(System.out::println);
						
						Map<String,String> map = new HashMap<String, String>();
						for (String var : resp.getButtonVar())
						{
							String[] splits = var.replace("\"", "").split("=");
							map.put(splits[0], splits[1]);
						}
						map.put("payPalId", button.getHostedButtonID());
						PayPalButton b = new PayPalButton(map);
						Date date  = sdf.parse(button.getModifyDate().replace("Z", ""));
						b.setModifiedDate(date);
						lazyLoadResult.add(b);
						
//						System.out.print(button.getItemName()+" "+map.get("item_number"));

					} else {
						
						StringBuilder sb = new StringBuilder();
						sb.append(resp.getAck().toString()+"\n");
						for (ErrorType error : resp.getErrors())
						{
							sb.append(error.getLongMessage()+"\n");
							System.out.println(error.getLongMessage());
						}
						throw new RuntimeException("unable to communicate to PayPal! - "+sb.toString());
					}
				}
			}
			
			
			for (PayPalButton button:lazyLoadResult)
			{
				if (textArea!=null) 
				{
					String text = textArea.getText();
					text=text.substring(0, text.indexOf("Downloading:")+"Downloading:".length());
					text+=" "+(((double) 100*count++)/(2*lazyLoadResult.size()))+"%";
					textArea.setText(text);
				}
				
				BMGetInventoryReq request = new BMGetInventoryReq();
				BMGetInventoryRequestType reqType = new BMGetInventoryRequestType();
				
				//(Required) The ID of the hosted button whose details you want to obtain.
				reqType.setHostedButtonID(button.getPayPalId());

				request.setBMGetInventoryRequest(reqType);
				
				// ## Making API call
				// Invoke the appropriate method corresponding to API in service
				// wrapper object
				BMGetInventoryResponseType resp = service.bMGetInventory(request);
				if (resp != null) {
					if (resp.getAck().toString().equalsIgnoreCase("SUCCESS")) {
						
						
						button.setQuantity(Integer.parseInt(resp.getItemTrackingDetails().getItemQty()));
					} else {
						
						StringBuilder sb = new StringBuilder();
						sb.append(resp.getAck().toString()+"\n");
						for (ErrorType error : resp.getErrors())
						{
							sb.append(error.getLongMessage()+"\n");
							System.out.println(error.getLongMessage());
						}
						continue;
					}
				}
			}
			
			if (textArea!=null) 
			{
				String text = textArea.getText();
				text=text.substring(0, text.indexOf("Downloading:"));
				textArea.setText(text);
			}
			
			result.addAll(lazyLoadResult);
			
			return result;

//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (SAXException e) {
//			e.printStackTrace();
//		} catch (ParserConfigurationException e) {
//			e.printStackTrace();
//		} catch (SSLConfigurationException e) {
//			e.printStackTrace();
//		} catch (InvalidCredentialException e) {
//			e.printStackTrace();
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		} catch (HttpErrorException e) {
//			e.printStackTrace();
//		} catch (InvalidResponseDataException e) {
//			e.printStackTrace();
//		} catch (ClientActionRequiredException e) {
//			e.printStackTrace();
//		} catch (MissingCredentialException e) {
//			e.printStackTrace();
//		} catch (OAuthException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//
//		return null;
	}
	
	public boolean deleteButton(PayPalButton button) throws Exception
	{

		
	// Configuration map containing signature credentials and other required configuration.

			Map<String,String> configurationMap =  Configuration.getAcctAndConfig();
			
			// Creating service wrapper object to make an API call by loading configuration map. 
			PayPalAPIInterfaceServiceService service = new PayPalAPIInterfaceServiceService(configurationMap);
				
			
			BMManageButtonStatusReq request = new BMManageButtonStatusReq();
			BMManageButtonStatusRequestType reqType = new BMManageButtonStatusRequestType();
			
			//(Required) The ID of the hosted button whose status you want to change.
			reqType.setHostedButtonID(button.getPayPalId());
			
			/*
			 *  (Required) The new status of the button. It is one of the following values:
				 DELETE - the button is deleted from PayPal
			 */
			reqType.setButtonStatus(ButtonStatusType.DELETE);
			request.setBMManageButtonStatusRequest(reqType);
			
			// ## Making API call
			// Invoke the appropriate method corresponding to API in service
			// wrapper object
			BMManageButtonStatusResponseType resp = service.bMManageButtonStatus(request);
			
			if (resp != null) {
				if (resp.getAck().toString().equalsIgnoreCase("SUCCESS")) {
					
					
					return true;
				} else {
					
					StringBuilder sb = new StringBuilder();
					sb.append(resp.getAck().toString()+"\n");
					for (ErrorType error : resp.getErrors())
					{
						sb.append(error.getLongMessage()+"\n");
						System.out.println(error.getLongMessage());
					}
					throw new RuntimeException("unable to communicate to PayPal! - "+sb.toString());
				}
			}

		return false;
	}
	public boolean createButton(PayPalButton button) throws Exception
	{

		
	// Configuration map containing signature credentials and other required configuration.

			Map<String,String> configurationMap =  Configuration.getAcctAndConfig();
			
			// Creating service wrapper object to make an API call by loading configuration map. 
			PayPalAPIInterfaceServiceService service = new PayPalAPIInterfaceServiceService(configurationMap);
				
			{
				BMCreateButtonReq request = new BMCreateButtonReq();
				BMCreateButtonRequestType reqType = new BMCreateButtonRequestType();
				
				reqType.setButtonType(ButtonTypeType.CART);
	
				reqType.setButtonCode(ButtonCodeType.HOSTED);
				
				//(Optional) HTML standard button variables
				List<String> lst = new ArrayList<String>();
				
				lst.add("item_name="+button.itemName);
				lst.add("amount="+button.price);
				lst.add("item_number="+button.stockId);
	
				reqType.setButtonVar(lst);
				
				request.setBMCreateButtonRequest(reqType);
				BMCreateButtonResponseType resp = service.bMCreateButton(request);
				
				
				if (resp != null) {
					if (resp.getAck().toString().equalsIgnoreCase("SUCCESS")) {
						button.setPayPalId(resp.getHostedButtonID());
					}
					else
					{
					
					StringBuilder sb = new StringBuilder();
					sb.append(resp.getAck().toString()+"\n");
					for (ErrorType error : resp.getErrors())
					{
						sb.append(error.getLongMessage()+"\n");
					}
					throw new RuntimeException("unable to communicate to PayPal! - "+sb.toString());
					}
				}
			}
			
			{
				BMSetInventoryReq request = new BMSetInventoryReq();
				BMSetInventoryRequestType reqType = new BMSetInventoryRequestType();
				
				reqType.setTrackInv("1");
				reqType.setTrackPnl("0");
				reqType.setHostedButtonID(button.getPayPalId());
				ItemTrackingDetailsType itemTrackingDetails = new ItemTrackingDetailsType();
				itemTrackingDetails.setItemQty(""+button.getQuantity());
				reqType.setItemTrackingDetails(itemTrackingDetails );
				
				
				request.setBMSetInventoryRequest(reqType);
				BMSetInventoryResponseType resp = service.bMSetInventory(request);
				
				
				if (resp != null) {
					if (resp.getAck().toString().equalsIgnoreCase("SUCCESS")) {
						return true;
					}
					else
					{
					
					StringBuilder sb = new StringBuilder();
					sb.append(resp.getAck().toString()+"\n");
					for (ErrorType error : resp.getErrors())
					{
						sb.append(error.getLongMessage()+"\n");
					}
					throw new RuntimeException("unable to communicate to PayPal! - "+sb.toString());
					}
				}
			}
			
		return false;
	}
	
	public static void main(String[] args) throws Exception
	{
		// TODO Auto-generated method stub

		PayPalConnector palConnector=new PayPalConnector();
		List<PayPalButton> buttons = palConnector.getPayPalButtons();
		buttons.forEach(System.out::println);
		
		for(PayPalButton button:buttons)
		{
			System.out.println("Attempting to delete "+button.itemName+"("+button.getPayPalId()+")...");
			
			if (palConnector.deleteButton(button))
			{
				System.out.println(button.itemName+"("+button.getPayPalId()+") successfully deleted.");
			}
			else
			{
				System.out.println(button.itemName+"("+button.getPayPalId()+") FAILED to be deleted.");
			}
		}
		
		PayPalButton button = new PayPalButton();
		button.setItemName("Test123");
		button.setPrice("74.22");
		button.setQuantity(100);
		button.setStockId("ZZ001");
		palConnector.createButton(button);
		
		System.out.println("Attempting to create "+button.itemName+"...");
		
		if (palConnector.createButton(button))
		{
			System.out.println(button.itemName+"("+button.getPayPalId()+") successfully created.");
		}
		else
		{
			System.out.println(button.itemName+" FAILED to be created.");
		}
		
	}

	public boolean updateButton(PayPalButton button) throws Exception
	{
		// Configuration map containing signature credentials and other required configuration.

		Map<String,String> configurationMap =  Configuration.getAcctAndConfig();
		
		// Creating service wrapper object to make an API call by loading configuration map. 
		PayPalAPIInterfaceServiceService service = new PayPalAPIInterfaceServiceService(configurationMap);
		
		BMSetInventoryReq request = new BMSetInventoryReq();
		BMSetInventoryRequestType reqType = new BMSetInventoryRequestType();
		
		//API Version
		reqType.setVersion("82");	
		
		//(Required) The ID of the hosted button whose inventory you want to set.
		reqType.setHostedButtonID(button.getPayPalId());
		
		/*
		 *  (Required) Whether to track inventory levels associated with the button. 
		 *  It is one of the following values:
		    0 - do not track inventory
		    1 - track inventory
		 */
		reqType.setTrackInv("1");
		
		/*
		 *  (Required) Whether to track the gross profit associated with inventory changes. 
		 *  It is one of the following values:
		    0 - do not track the gross profit
		    1 - track the gross profit
			Note: The gross profit is calculated as the price of the item less its cost, 
			multiplied by the change in the inventory level since the last call to 
			BMSetInventory
		 */
		reqType.setTrackPnl("0");
		ItemTrackingDetailsType itemTrackDetails = new ItemTrackingDetailsType();
		reqType.setItemTrackingDetails(itemTrackDetails);
		request.setBMSetInventoryRequest(reqType);
		
		/*
		 * The quantity you want to specify for the item associated with this button. 
		 * Specify either the absolute quantity in this field or the change in quantity in 
		 * the quantity delta field
		 */
		itemTrackDetails.setItemQty(""+button.getQuantity());
		
				
		// ## Making API call
		// Invoke the appropriate method corresponding to API in service
		// wrapper object
		BMSetInventoryResponseType resp = service.bMSetInventory(request);

		if (resp != null) {
			if (resp.getAck().toString().equalsIgnoreCase("SUCCESS")) {
				return true;
			}
			else
			{
			
			StringBuilder sb = new StringBuilder();
			sb.append(resp.getAck().toString()+"\n");
			for (ErrorType error : resp.getErrors())
			{
				sb.append(error.getLongMessage()+"\n");
			}
			throw new RuntimeException("unable to communicate to PayPal! - "+sb.toString());
			}
		}
		return false;
	}
		

}
