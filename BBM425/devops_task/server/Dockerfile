FROM python:3.11.4-slim AS server-builder
WORKDIR /app/server/
COPY server /app/server
RUN pip install -r requirements.txt
CMD python app.py
