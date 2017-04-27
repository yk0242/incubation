# -*- coding: utf-8 -*-

# test logging to file and to HTTPS
# based on https://docs.python.org/3.4/howto/logging.html#configuring-logging
# see also https://docs.python.org/3/library/logging.handlers.html#timedrotatingfilehandler
#          https://docs.python.org/3/library/logging.handlers.html#httphandler

# import logging
import logging.handlers
import ssl

# create logger
logger = logging.getLogger(__name__)
logger.setLevel(logging.INFO)

# create time rotating file handler and set level to INFO
fh = logging.handlers.TimedRotatingFileHandler('logs/log_test.log', 'D', 1, 366, 'UTF8')
fh.setLevel(logging.INFO)

# create HTTPS handler and set level to INFO
empty_context = ssl.SSLContext(ssl.PROTOCOL_TLSv1_2)  # insecure--only for testing purposes

hh = logging.handlers.HTTPHandler('localhost:443', 'https://localhost/random/server_stub.php',
                                  method='GET', secure=True, credentials=None, context=empty_context)
hh.setLevel(logging.WARNING)

# create formatter
formatter = logging.Formatter('%(asctime)s - %(name)s - %(levelname)s - %(message)s')

# add formatter to fh
fh.setFormatter(formatter)

# add handlers to logger
logger.addHandler(fh)
logger.addHandler(hh)

# 'application' code
logger.debug('debug message')
logger.info('info message')
logger.warning('warn message')
logger.error('error message')
logger.critical('critical message')
