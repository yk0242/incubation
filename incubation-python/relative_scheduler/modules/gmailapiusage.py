"""
    Gmail API Usage class.
"""

import httplib2
import os
import base64

from email.mime.text import MIMEText
from apiclient import discovery
from googleapiclient.errors import HttpError
from oauth2client import client
from oauth2client import tools
from oauth2client.file import Storage

import logging
logger = logging.getLogger(__name__)


class GmailApiUsage:
    SCOPES = 'https://www.googleapis.com/auth/gmail.send'
    CLIENT_SECRET_FILE = 'resources/client_secret.json'
    APPLICATION_NAME = 'relative_scheduler'

    @staticmethod
    def get_credentials():
        """Gets valid user credentials from storage.
        If nothing has been stored, or if the stored credentials are invalid,
        the OAuth2 flow is completed to obtain the new credentials.
        Returns:
            Credentials, the obtained credential.
        """
        home_dir = os.path.expanduser('~')
        credential_dir = os.path.join(home_dir, '.credentials')
        if not os.path.exists(credential_dir):
            os.makedirs(credential_dir)
        credential_path = os.path.join(credential_dir,
                                       GmailApiUsage.APPLICATION_NAME + '.json')

        store = Storage(credential_path)
        credentials = store.get()
        if not credentials or credentials.invalid:
            flow = client.flow_from_clientsecrets(GmailApiUsage.CLIENT_SECRET_FILE, GmailApiUsage.SCOPES)
            flow.user_agent = GmailApiUsage.APPLICATION_NAME
            credentials = tools.run_flow(flow, store, None)
            logger.info('Storing credentials to ' + credential_path)
        return credentials

    @staticmethod
    def _create_message(sender, to, subject, message_text):
        """Create a message for an email.
        Args:
            sender: Email address of the sender.
            to: Email address of the receiver.
            subject: The subject of the email message.
            message_text: The text of the email message.
        Returns:
            An object containing a base64url encoded email object.
        """
        message = MIMEText(message_text)
        message['to'] = to
        message['from'] = sender
        message['subject'] = subject
        logger.info(message.as_string())
        return {'raw': base64.urlsafe_b64encode(message.as_string().encode('ascii')).decode("utf-8")}

    @staticmethod
    def _send_message(service, user_id, message):
        """Send an email message.
        Args:
            service: Authorized Gmail API service instance.
            user_id: User's email address. The special value "me"
            can be used to indicate the authenticated user.
            message: Message to be sent.
        Returns:
            Sent Message.
        """
        try:
            message = (service.users().messages().send(userId=user_id, body=message).execute())
            logger.info('Message Id: %s' % message['id'])
            return message
        except HttpError as error:
            logger.error('An error occurred: %s' % error)

    @staticmethod
    def send_mail(msg, title, to_address):
        """sends e-mail with msg and title to to_address"""
        credentials = GmailApiUsage.get_credentials()
        http = credentials.authorize(httplib2.Http())
        service = discovery.build('gmail', 'v1', http=http)
        message = GmailApiUsage._create_message('me', to_address, title, msg)
        GmailApiUsage._send_message(service, 'me', message)
