package com.serpstat.restapi;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.serpstat.restapi.model.Keyword;

public class CsvTest {
	private final String FILE_NAME = "file/keyword.csv";
	private final String FILE_NAME_OUTPUT = System.getProperty("java.io.tmpdir") + "keyword.csv";

	public static void main(String args[]) {
		try {
			CsvTest app = new CsvTest();
			app.testCsvRead();
			app.testCsvWrite();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void testCsvRead() throws Exception {
		System.out.println("read csv");

		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(FILE_NAME).getFile());

		CsvSchema schema = CsvSchema.emptySchema().withHeader().withColumnSeparator(',');
		CsvMapper mapper = new CsvMapper();

		try {
			MappingIterator<Keyword> mi = mapper.readerFor(Keyword.class).with(schema).readValues(file);
			while (mi.hasNext()) {
				Keyword keyword = mi.next();
				System.out.println(keyword);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void testCsvWrite() throws Exception {
		// initialize our list
		List<Keyword> list = new ArrayList<>();
		list.add(populateKeyword(1));
		list.add(populateKeyword(2));
		list.add(populateKeyword(3));

		// initialize and configure the mapper
		CsvMapper mapper = new CsvMapper();
		// we ignore unknown fields or fields not specified in schema, otherwise
		// writing will fail
		mapper.configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true);

		// initialize the schema
		CsvSchema schema = CsvSchema.builder().addColumn("id").addColumn("siteId").addColumn("keyword").build();

		// map the bean with our schema for the writer
		ObjectWriter writer = mapper.writerFor(Keyword.class).with(schema);

		System.out.println(FILE_NAME_OUTPUT);
		File tempFile = new File(FILE_NAME_OUTPUT);
		// we write the list of objects
		writer.writeValues(tempFile).writeAll(list);
	}

	/**
	 * Initialize an OfferTemplateCategory using index as suffix.
	 * 
	 * @param index
	 * @return
	 */
	private Keyword populateKeyword(long index) {
		Keyword o1 = new Keyword();
		o1.setId(index);
		o1.setSiteId(1L);
		o1.setKeyword("Keyword_" + index);

		return o1;
	}
}
