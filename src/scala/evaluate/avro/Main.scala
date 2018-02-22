package evaluate.avro

import org.apache.avro.Schema
import org.apache.avro.Schema.Parser
import org.apache.avro.generic.GenericData
import org.apache.avro.generic.GenericRecord
import org.apache.avro.specific.SpecificDatumWriter
import org.apache.avro.specific.SpecificDatumReader
import java.io.ByteArrayOutputStream
import org.apache.avro.io._

object Main{
	val SCHEMA_STRING = """
	{
    "namespace": "kakfa-avro.test",
     "type": "record",
     "name": "user",
     "fields":[
         {  "name": "id", "type": "int"},
         {   "name": "name",  "type": "string"},
         {   "name": "email", "type": ["string", "null"]}
     ]
	}
	"""
	val schema: Schema = new Schema.Parser().parse(SCHEMA_STRING)
	val writer = new SpecificDatumWriter[GenericRecord](schema)
	val reader: DatumReader[GenericRecord] = new SpecificDatumReader[GenericRecord](schema)


      def main(args: Array[String]){
		val genericRecord: GenericRecord = new GenericData.Record(schema)
		genericRecord.put("id", 1)
		genericRecord.put("name", "singh")
		genericRecord.put("email", null)

import java.util.Date
		val stime = new Date
		for(i <- 1 to 100000) {
			val serializedBytes: Array[Byte] = serialize(genericRecord, writer)
			val resultRecord: GenericRecord = deserialize(serializedBytes, reader)	
		}
		val etime = new Date
		printf("stime= %s , etime= %s \n" , stime , etime)
		printf("elapsed time= %s \n" , etime.getTime - stime.getTime)
	  }

	  def serialize(genericRecord: GenericRecord,
	                writer: SpecificDatumWriter[GenericRecord]): Array[Byte] = {
		val out = new ByteArrayOutputStream()
		val encoder: BinaryEncoder = EncoderFactory.get().binaryEncoder(out, null)
		writer.write(genericRecord, encoder)
		encoder.flush()
		out.close()
		out.toByteArray()
	  }

	  def deserialize(serializedBytes: Array[Byte],
	                  reader: DatumReader[GenericRecord]): GenericRecord = {
		val decoder: Decoder = DecoderFactory.get().binaryDecoder(serializedBytes, null)
	    reader.read(null, decoder)
	  }
}