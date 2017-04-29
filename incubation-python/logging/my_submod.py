# -*- coding: utf-8 -*-

# submodule test

# ### these two lines to be used at top of EACH AND EVERY module ###
import logging
logger = logging.getLogger(__name__)
# ###


def myloop():
    count = 2000000
    for i in range(2 * count + 1):
        if i % count == 0:
            logger.info("current count is %s", i)
    logger.warning("returning without doing anything...?")
