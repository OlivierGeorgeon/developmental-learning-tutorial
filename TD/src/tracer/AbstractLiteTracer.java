package tracer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;
import java.util.Arrays;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * This tracer logs the trace by sending it to a remote php script.
 */
public class AbstractLiteTracer implements Tracer<Element>
{

	private Document m_document;
	private Element m_currentEvent;
	private String m_traceId;
	private int m_id = 0;

	private boolean m_eventStarted;

	private DocumentBuilder m_builder;

	private Transformer m_transformer;

	private Element m_root;

	private int m_last_t;

	private String url;
	
	private String cookie;
	
	/**
	 * Initialize the tracer.
	 * @param url The URL of AbstractLite where to send the trace.
	 * @param cookie The cookie of the user of AbstractLite.
	 */
	public AbstractLiteTracer(String url, String cookie)
	{
		this.url = url;
		this.cookie = cookie;
		m_traceId = "test";
		this.post_new_trace();
		
		m_eventStarted = false;
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		m_builder = null;
		try {
			m_builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e1) {
			System.err.println("Error creating the DOM builder.");
			e1.printStackTrace();
		}
		
		TransformerFactory tfactory = TransformerFactory.newInstance();
		try {
			m_transformer = tfactory.newTransformer();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		}
		m_transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		m_transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		m_transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
		
		m_document = m_builder.newDocument();
	} 
	
	protected void post_obsel(String obselData)
	{
		try
		{
			URL base = new URL(this.url + "streamTrace.php");
			HttpURLConnection baseCon = (HttpURLConnection) base.openConnection();
			baseCon.setRequestMethod("POST");

			String data = URLEncoder.encode("traceId", "UTF-8") + "="
					+ URLEncoder.encode(m_traceId, "UTF-8");
			data += "&" + URLEncoder.encode("streamcookie", "UTF-8") + "="
					+ URLEncoder.encode(this.cookie, "UTF-8");
			data += "&" + URLEncoder.encode("data", "UTF-8") + "="
					+ URLEncoder.encode(obselData, "UTF-8");

			//System.err.println(data);
			
			baseCon.setDoOutput(true);
			baseCon.setDoInput(true);
			baseCon.setUseCaches (false);
			
			OutputStream os = baseCon.getOutputStream();
			os.write(data.getBytes());
			os.flush();
			os.close();
			
			int response = baseCon.getResponseCode();
			if(response >= 300 || response < 200)
			{
				System.err.println("Post failed: \"\"\"");
				InputStream es = baseCon.getErrorStream();
				for(int ch = es.read(); ch != -1; ch = es.read())
				{
					System.err.print((char)ch);
				}
				System.err.println("\"\"\"");
			}else{
				System.err.println("Done.");
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
			System.err.println("Invalid URL (" + this.url + ").");
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Couldn't reach the server (" + this.url + ").");
			System.exit(0);
		};
	}

	protected void post_new_trace()
	{
		try
		{
			URL base = new URL(this.url + "newStreamedTrace.php");
			HttpURLConnection baseCon = (HttpURLConnection) base.openConnection();
			baseCon.setRequestMethod("POST");

			String data = URLEncoder.encode("streamcookie", "UTF-8") + "="
					+ URLEncoder.encode(this.cookie, "UTF-8");
			/*data += URLEncoder.encode("traceId", "UTF-8")+
			            "="+URLEncoder.encode(m_traceId, "UTF-8");*/

			//System.err.println(data);
			
			baseCon.setDoOutput(true);
			baseCon.setDoInput(true);
			baseCon.setUseCaches (false);
			
			OutputStream os = baseCon.getOutputStream();
			os.write(data.getBytes());
			os.flush();
			os.close();
			
			int response = baseCon.getResponseCode();
			if(response >= 300 || response < 200)
			{
				System.err.println("Post failed: \"\"\"");
				InputStream es = baseCon.getErrorStream();
				for(int ch = es.read(); ch != -1; ch = es.read())
				{
					System.err.print((char)ch);
				}
				System.err.println("\"\"\"");
			}else{
				InputStream is = baseCon.getInputStream();
				byte[] b = new byte[1000];
				int len = is.read(b);
				m_traceId = new String(Arrays.copyOf(b, len));
				System.err.println("Done.");
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
			System.err.println("Invalid URL (" + this.url + ").");
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Couldn't reach the server (" + this.url + ").");
			System.exit(0);
		};
	}
	
	protected void post_end_of_trace()
	{
		try
		{
			URL base = new URL(this.url + "endOfStream.php");
			HttpURLConnection baseCon = (HttpURLConnection) base.openConnection();
			baseCon.setRequestMethod("POST");

			String data = URLEncoder.encode("traceId", "UTF-8") + "="
					+ URLEncoder.encode(m_traceId, "UTF-8");
			data += "&" + URLEncoder.encode("streamcookie", "UTF-8") + "="
					+ URLEncoder.encode(this.cookie, "UTF-8");

			//System.err.println(data);
			
			baseCon.setDoOutput(true);
			baseCon.setDoInput(true);
			baseCon.setUseCaches (false);
			
			OutputStream os = baseCon.getOutputStream();
			os.write(data.getBytes());
			os.flush();
			os.close();
			
			int response = baseCon.getResponseCode();
			if(response >= 300 || response < 200)
			{
				System.err.println("Post failed: \"\"\"");
				InputStream es = baseCon.getErrorStream();
				for(int ch = es.read(); ch != -1; ch = es.read())
				{
					System.err.print((char)ch);
				}
				System.err.println("\"\"\"");
			}else{
				System.err.println("Done.");
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
			System.err.println("Invalid URL (" + this.url + ").");
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Couldn't reach the server (" + this.url + ").");
			System.exit(0);
		};
	}
	
	/**
	 * Write the XML document to the file trace.xml
	 * from http://java.developpez.com/faq/xml/?page=xslt#creerXmlDom
	 */
	public boolean close() 
	{
		this.post_end_of_trace();
		return true;
	}

	/**
	 * Create an event that can be populated using its reference.
	 * @param type The event's type.
	 * @param t The event's time stamp.
	 * @return The pointer to the event.
	 */
	public Element newEvent(String source, String type, int t)
	{
		if(!m_eventStarted)
		{
			m_root = m_document.createElement("slice");
			m_root.setAttribute("date", Integer.toString(t));
			m_document.appendChild(m_root);
		}else if(t != m_last_t)
		{
			this.finishEvent();
			m_root = m_document.createElement("slice");
			m_root.setAttribute("date", Integer.toString(t));
			m_document.appendChild(m_root);
		}
		m_last_t = t;
		m_eventStarted = true;
		
		m_currentEvent = m_document.createElement("event");
		m_currentEvent.setAttribute("id", Integer.toString(m_id));
		m_currentEvent.setAttribute("source", source);
		m_currentEvent.setAttribute("date", Integer.toString(t));
		Element typeElement = m_document.createElement("type");
		typeElement.setTextContent(type);
		m_currentEvent.appendChild(typeElement);
		m_root.appendChild(m_currentEvent);
		m_id++;

		return m_currentEvent;
	}
	
	/**
	 * Create a new event that can be populated with elements later.
	 * @param t the time stamp
	 */
	public void startNewEvent(int t)
	{
		this.newEvent("Ernest", "action", t);
	}
	
	public void finishEvent()
	{
		if(!m_eventStarted)
		{
			return;
		}
		
		m_eventStarted = false;
		
		Source source = new DOMSource(m_document);
		StringWriter sw = new StringWriter();
		Result result = new StreamResult(sw);
		try {
			m_transformer.transform(source, result);
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		
		this.post_obsel(sw.toString());
		
		m_document.removeChild(m_root);
	}

	/**
	 * Add a new element to the current event
	 * @param name The element's name
	 * @return a pointer to the element that can be used to add sub elements.
	 */
	public Element addEventElement(String name)
	{
		return this.addEventElementImpl(name, "", true);
	}
	
	public void addEventElement(String name, String textContent)
	{
		this.addEventElementImpl(name, textContent, true);
	}
	
	private Element addEventElementImpl(String name, String textContent, boolean display)
	{
		if (m_currentEvent != null)
		{
			Element element = m_document.createElement(name);
			element.setTextContent(textContent);
			if (!display) element.setAttribute("display","no");
			m_currentEvent.appendChild(element);
			return element;
		}
		else 
			return null;
	}

	public Element addSubelement(Element element, String name)
	{
		return this.addSubelementImpl(element, name, "");
	}
	
	public void addSubelement(Element element, String name, String textContent)
	{
		this.addSubelementImpl(element, name, textContent);
	}
	
	private Element addSubelementImpl(Element element, String name, String textContent)
	{
		if (element != null)
		{
			Element subElement = m_document.createElement(name);
			subElement.setTextContent(textContent);
			element.appendChild(subElement);
			return subElement;
		}
		else return null;
	}

	public Element addEventElement(String name, boolean display) 
	{
		return this.addEventElementImpl(name, "", display);
	}
}
