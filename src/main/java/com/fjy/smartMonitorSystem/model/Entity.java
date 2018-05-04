package com.fjy.smartMonitorSystem.model;

import java.io.Serializable;
import java.net.URI;
import java.util.Arrays;
import java.util.LinkedHashSet;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;


public class Entity<T> implements Serializable{
	private int code;
	private String msg;
	private T data;
	
	public Entity(){
		
	}

	public Entity(int code, String msg, T data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	
	
	@Override
	public String toString() {
		return "Entity [code=" + code + ", msg=" + msg + ", data=" + data + "]";
	}

	/**
	 * 
	 * Entity.builder(200).header("a", "a").build(1, "msg", data);
	 * 
	 * @param status
	 * @return
	 */
	public static EntityBuilder builder(int status) {
		return EntityBuilder.create(status);
	}

	
	/**
	 * 
	 * Entity.success(body);
	 * 
	 * @param code
	 * @param msg
	 * @return
	 */
	public static <T> ResponseEntity<Entity<T>> success(T body) {
		EntityBuilder builder = EntityBuilder.create(200);
		return builder.build(0, "", body);
	}

	/**
	 * 
	 * Entity.failure(20, "登陆失败");
	 * 
	 * @param body
	 * @return
	 */
	public static <T> ResponseEntity<Entity<T>> failure(int code, String msg) {
		EntityBuilder builder = EntityBuilder.create(400);
		return builder.build(code, msg, null);
	}

	public static class EntityBuilder implements Serializable{

		private final HttpHeaders headers = new HttpHeaders();
		private final HttpStatus status;

		private EntityBuilder(HttpStatus status) {
			this.status = status;
		}

		public static EntityBuilder create(int status) {
			return new EntityBuilder(HttpStatus.valueOf(status));
		}

		public EntityBuilder header(String headerName, String... headerValues) {
			for (String headerValue : headerValues) {
				this.headers.add(headerName, headerValue);
			}
			return this;
		}

		public EntityBuilder headers(HttpHeaders headers) {
			if (headers != null) {
				this.headers.putAll(headers);
			}
			return this;
		}

		public EntityBuilder allow(HttpMethod... allowedMethods) {
			this.headers.setAllow(new LinkedHashSet<HttpMethod>(Arrays.asList(allowedMethods)));
			return this;
		}

		public EntityBuilder contentLength(long contentLength) {
			this.headers.setContentLength(contentLength);
			return this;
		}

		public EntityBuilder contentType(MediaType contentType) {
			this.headers.setContentType(contentType);
			return this;
		}

		public EntityBuilder eTag(String eTag) {
			if (eTag != null) {
				if (!eTag.startsWith("\"") && !eTag.startsWith("W/\"")) {
					eTag = "\"" + eTag;
				}
				if (!eTag.endsWith("\"")) {
					eTag = eTag + "\"";
				}
			}
			this.headers.setETag(eTag);
			return this;
		}

		public EntityBuilder lastModified(long date) {
			this.headers.setLastModified(date);
			return this;
		}

		public EntityBuilder location(URI location) {
			this.headers.setLocation(location);
			return this;
		}
 
		public <T> ResponseEntity<Entity<T>> build(int code, String msg, T data) {
			return new ResponseEntity<Entity<T>>(new Entity<T>(code, msg, data), this.status);
		}
	}
	
}
