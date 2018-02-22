# -*- coding:utf-8 -*-
import cStringIO
import avro.schema
import avro.io
from logging import getLogger, StreamHandler, DEBUG, basicConfig, INFO
from avro_serde import AvroSerde 
from datetime import datetime

basicConfig()
logger = getLogger(__name__)
logger.setLevel(INFO)

schemaDescription = """
{"namespace": "example.avro",
 "type": "record",
 "name": "User",
 "fields": [
     {"name": "name", "type": "string"},
     {"name": "id",  "type": ["int", "null"]}
 ]
}
"""

print(schemaDescription)

schema = AvroSerde.gen_schema(schemaDescription)
data = {u'name':u'たろう', u'id':1}

class AvroEvaluater:
	@staticmethod
	def main():

		try_count=100000
		logger.info("try_count= %s " , try_count)
		start_time=datetime.now()
		for n in range(try_count):
			# StringIOからバイトデータを取り出し
			bytes = AvroSerde.serialize(data, schema)
			# 取得したバイト長
			logger.debug("バイト長:%d" , len(bytes))
			# --- デシリアライズ
			logger.debug(AvroSerde.deserialize(bytes,schema))
		end_time=datetime.now()
		logger.info("stime= %s , etime= %s" , start_time , end_time)
		logger.info("elapsed time= %s " , end_time - start_time)


