package com.tower.service.rpc.impl;

import io.searchbox.action.Action;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.JestResult;
import io.searchbox.client.JestResultHandler;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.Delete;
import io.searchbox.core.Get;
import io.searchbox.core.Index;
import io.searchbox.core.Update;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.mapping.PutMapping;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import com.tower.service.domain.IDTO;
import com.tower.service.log.Logger;
import com.tower.service.log.LoggerFactory;
import com.tower.service.rpc.IClient;

public class ESClient implements JestClient, IClient {

	protected Logger logger = LoggerFactory.getLogger(getClass());
	private HttpClientConfig clientConfig;
	private JestClient client;
	private JestClientFactory factory;
	private boolean discoveryEnabled = true;
	private long time = 1l;
	private TimeUnit unit = TimeUnit.SECONDS;
	private String filter;
	private boolean inited;

	@PostConstruct
	public void init() {
		Set<String> servers = new LinkedHashSet<String>();
		servers.add("http://192.168.0.158:9200");
		clientConfig = new HttpClientConfig.Builder(servers)
				.discoveryEnabled(discoveryEnabled)
				.discoveryFrequency(time, unit).multiThreaded(true).build();
		factory = new JestClientFactory();
		factory.setHttpClientConfig(clientConfig);
		client = factory.getObject();
		inited = true;
	}

	@Override
	public <T extends JestResult> T execute(Action<T> clientRequest)
			throws IOException {
		return client.execute(clientRequest);
	}

	@Override
	public void shutdownClient() {
		client.shutdownClient();
	}

	@Override
	public void setServers(Set<String> servers) {
		client.setServers(servers);
	}

	@Override
	public <T extends JestResult> void executeAsync(Action<T> clientRequest,
			JestResultHandler<? super T> jestResultHandler) {
		client.executeAsync(clientRequest, jestResultHandler);
	}

	@Override
	public void createIndex(String idxName) {
		try {
			execute(new CreateIndex.Builder(idxName).build());
		} catch (IOException e) {
			logger.error("createIndex error with:idxName=" + idxName, e);
		}
	}

	@Override
	public void createIndex(String idxName, String settingsJson) {
		try {
			execute(new CreateIndex.Builder(idxName).settings(settingsJson)
					.build());
		} catch (IOException e) {
			logger.error("createIndex error with:idxName=" + idxName
					+ " ,settingsJson=" + settingsJson, e);
		}
	}

	@Override
	public void createIndex(IDTO source, String idxName, String idxTypeName) {
		Index index = new Index.Builder(source).index(idxName)
				.type(idxTypeName).build();
		try {
			client.execute(index);
		} catch (IOException e) {
			logger.error("createIndex error with:source=" + source
					+ " ,idxName=" + idxName + " ,idxTypeName=" + idxTypeName,
					e);
		}
	}

	@Override
	public void createIdxMap(String idxName, String idxType, String mapDefine) {
		PutMapping putMapping = new PutMapping.Builder(idxName, idxType,
				mapDefine).build();
		try {
			this.execute(putMapping);
		} catch (IOException e) {
			logger.error("createIdxMap error with:idxName=" + idxName
					+ " ,idxType=" + idxType + " ,mapDefine=" + mapDefine, e);
		}
	}

	@Override
	public void createIndex(IDTO source, String idxName, String idxTypeName,
			String id) {
		Index index = new Index.Builder(source).index(idxName)
				.type(idxTypeName).id(id).build();
		try {
			client.execute(index);
		} catch (IOException e) {
			logger.error("createIndex error with:source=" + source + " ,id="
					+ id + " ,idxName=" + idxName + " ,idxTypeName="
					+ idxTypeName, e);
		}
	}

	@Override
	public JestResult search(String idxName, String idxType, String id) {
		JestResult result = null;
		Get get = new Get.Builder(idxName, id).type(idxType).build();
		try {
			result = execute(get);
		} catch (Exception e) {
			logger.error("search error with:idxName=" + idxName + " ,idxType="
					+ idxType + " ,id=" + id, e);
		}

		return result;
	}

	@Override
	public void update(String idxName, String idxType, String id, String script) {
		try {
			execute(new Update.Builder(script).index(idxName).type(idxType)
					.id(id).build());
		} catch (IOException e) {
			logger.error("update error with:idxName=" + idxName + " ,idxType="
					+ idxType + " ,id=" + id + ",script=" + script, e);
		}
	}

	@Override
	public void delete(String idxName, String idxType, String id) {
		try {
			client.execute(new Delete.Builder(id).index(idxName).type(idxType)
					.build());
		} catch (IOException e) {
			logger.error("delete error with:idxName=" + idxName + " ,idxType="
					+ idxType + " ,id=" + id, e);
		}
	}

	public static void main(String args[]) {
		ESClient client = new ESClient();
		client.init();
		JestResult result = client.search("megacorp", "employee", "1");
		System.out.println(result.getJsonObject().toString());
		for (int i = 0; i < 1; i++) {
		}
	}
}
