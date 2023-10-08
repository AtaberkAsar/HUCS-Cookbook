from os import environ as env
from redis import Redis
from rq import Queue, Worker, Connection
import multiprocessing

class RedisService:
    def __init__(self):
        self.conn = Redis(
            host=env["REDIS_HOST"],
            port=env["REDIS_PORT"]
        )

        self.job_queue = Queue(
            name=env["REDIS_QUEUE_NAME"],
            connection=self.conn
        )
    
    def start_worker(self):
        with Connection(self.conn):
            worker = Worker(self.job_queue)
            worker.work()
    
    def create_workers(self, n = 1):
        for i in range(n):
            worker_process = multiprocessing.Process(target=self.start_worker)
            worker_process.start()
    
    def enqueue_job(self, otx_service, postgre_service, q, askedBy):
        self.job_queue.enqueue(otx_service.ask, q, askedBy, postgre_service)