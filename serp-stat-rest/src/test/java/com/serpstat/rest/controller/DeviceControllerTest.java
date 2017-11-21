package com.serpstat.rest.controller;

import static com.serpstat.rest.utils.JsonConverter.convertJsonToList;
import static com.serpstat.rest.utils.JsonConverter.convertJsonToMap;
import static com.serpstat.rest.utils.JsonConverter.convertObjectToJson;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.serpstat.rest.domain.Device;
import com.serpstat.rest.repository.DeviceRepository;

@RunWith(SpringRunner.class)
@WebMvcTest(value = DeviceController.class, secure = false)
public class DeviceControllerTest {
	final static String URI_DEVICE = "/api/v1/devices";

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private DeviceRepository deviceRepository;

	@Test
	public void listAllDevices() throws Exception {
		List<Device> devicesList = getDeviceList();

		when(deviceRepository.findAll()).thenReturn(devicesList);

		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.get(URI_DEVICE).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		verify(deviceRepository, atLeastOnce()).findAll();
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());

		List<Map<String, Object>> list = convertJsonToList(result.getResponse().getContentAsString());
		assertEquals(2, list.size());
	}

	@Test
	public void getDevice() throws Exception {
		Device device = getDeviceList().get(0);

		when(deviceRepository.findById(anyInt())).thenReturn(Optional.of(device));

		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.get(URI_DEVICE+"/"+device.getId()).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		verify(deviceRepository, atLeastOnce()).findById(anyInt());
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());

		Map<String, Object> map = convertJsonToMap(result.getResponse().getContentAsString());

		assertEquals(1,  map.get("id"));
	}

	@Test
	public void createDeviceWithSuccess() {
		Device newDevice = getNewDevice();
		String deviceJson = convertObjectToJson(newDevice);

		when(deviceRepository.findByName(anyString())).thenReturn(Optional.empty());
		when(deviceRepository.saveAndFlush(any(Device.class))).thenReturn(newDevice);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(URI_DEVICE)
				.accept(MediaType.APPLICATION_JSON).content(deviceJson).contentType(MediaType.APPLICATION_JSON);

		MvcResult result;
		try {
			result = mockMvc.perform(requestBuilder).andReturn();
			assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
			assertEquals("http://localhost"+URI_DEVICE+"/1", result.getResponse().getHeader(HttpHeaders.LOCATION));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void createDeviceWithConflict() {
		Device newDevice = getNewDevice();
		String deviceJson = convertObjectToJson(newDevice);

		when(deviceRepository.findByName(anyString())).thenReturn(Optional.of(newDevice));

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(URI_DEVICE)
				.accept(MediaType.APPLICATION_JSON).content(deviceJson).contentType(MediaType.APPLICATION_JSON);

		MvcResult result;
		try {
			result = mockMvc.perform(requestBuilder).andReturn();
			assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());

			Map<String, Object> map = convertJsonToMap(result.getResponse().getContentAsString());
			assertEquals(HttpStatus.CONFLICT.value(), ((Integer) map.get("errorCode")).intValue());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void updateDeviceWithSuccess() {
		Device newDevice = getNewDevice();
		String deviceJson = convertObjectToJson(newDevice);

		when(deviceRepository.findById(anyInt())).thenReturn(Optional.of(newDevice));
		when(deviceRepository.saveAndFlush(any(Device.class))).thenReturn(newDevice);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put(URI_DEVICE+"/"+newDevice.getId())
				.accept(MediaType.APPLICATION_JSON).content(deviceJson).contentType(MediaType.APPLICATION_JSON);

		MvcResult result;
		try {
			result = mockMvc.perform(requestBuilder).andReturn();
			assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void updateDeviceWithNotFound() {
		Device newDevice = getNewDevice();
		String deviceJson = convertObjectToJson(newDevice);

		when(deviceRepository.findById(anyInt())).thenReturn(Optional.empty());
		when(deviceRepository.saveAndFlush(any(Device.class))).thenReturn(newDevice);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put(URI_DEVICE+"/"+newDevice.getId())
				.accept(MediaType.APPLICATION_JSON).content(deviceJson).contentType(MediaType.APPLICATION_JSON);

		MvcResult result;
		try {
			result = mockMvc.perform(requestBuilder).andReturn();
			assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());

			Map<String, Object> map = convertJsonToMap(result.getResponse().getContentAsString());
			assertEquals(HttpStatus.NOT_FOUND.value(), ((Integer) map.get("errorCode")).intValue());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void deleteDeviceWithSuccess() {
		Device newDevice = getNewDevice();

		when(deviceRepository.findById(anyInt())).thenReturn(Optional.of(newDevice));

		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete(URI_DEVICE+"/"+newDevice.getId())
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result;
		try {
			result = mockMvc.perform(requestBuilder).andReturn();
			verify(deviceRepository, atLeastOnce()).deleteById(anyInt());

			assertEquals(HttpStatus.NO_CONTENT.value(), result.getResponse().getStatus());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void deleteDeviceWithNotFound() {
		Device newDevice = getNewDevice();
		String deviceJson = convertObjectToJson(newDevice);
		
		when(deviceRepository.findById(anyInt())).thenReturn(Optional.empty());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete(URI_DEVICE+"/"+newDevice.getId())
				.accept(MediaType.APPLICATION_JSON).content(deviceJson).contentType(MediaType.APPLICATION_JSON);

		MvcResult result;
		try {
			result = mockMvc.perform(requestBuilder).andReturn();
			verify(deviceRepository, never()).deleteById(anyInt());
			assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());

			Map<String, Object> map = convertJsonToMap(result.getResponse().getContentAsString());
			assertEquals(HttpStatus.NOT_FOUND.value(), ((Integer) map.get("errorCode")).intValue());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Device getNewDevice() {
		Device newDevice = new Device("tablet");
		newDevice.setId(1);
		return newDevice;
	}

	private List<Device> getDeviceList() {
		Device device1 = new Device("desktop");
		device1.setId(1);

		Device device2 = new Device("mobile");
		device2.setId(2);

		List<Device> devices = new ArrayList<>();
		devices.add(device1);
		devices.add(device2);
		return devices;
	}
}
