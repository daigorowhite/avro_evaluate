python_binary(
  name='main',
  dependencies=[
    ':lib',
  ],
  source='main.py',
)

python_library(
  name='lib',
  dependencies=[
    '3rdparty/python:avro',
  ],
  sources=globs('*.py'),
)