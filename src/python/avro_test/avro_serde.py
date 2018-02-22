# -*- coding:utf-8 -*-
import cStringIO
import avro.schema
import avro.io

class AvroSerde:
	@staticmethod
	def gen_schema(json_str):
		return avro.schema.parse(json_str)


	@staticmethod
	def serialize(data, schema):
		writer = cStringIO.StringIO()
		encoder = avro.io.BinaryEncoder(writer)
		datum_writer = avro.io.DatumWriter(schema)
		datum_writer.write(data, encoder)
		# StringIOからバイトデータを取り出し
		bytes = writer.getvalue()
		writer.close()
		return bytes 


	@staticmethod
	def deserialize(bytes, schema):
		reader = cStringIO.StringIO(bytes)
		decoder = avro.io.BinaryDecoder(reader)
		datum_reader = avro.io.DatumReader(schema)
		# オブジェクトの復元
		return datum_reader.read(decoder)