jvm_binary(name='bin',
  dependencies = [
    ':lib',
  ],
  source = 'Main.scala',
  main = 'evaluate.avro.Main',
)

scala_library(
  name='lib',
  sources = globs('*.scala'),
  dependencies = [
    ':dependencies',
  ],
)

java_library(
  name='dependencies',
  dependencies = [
   '3rdparty/jvm:avro',
  ],
)