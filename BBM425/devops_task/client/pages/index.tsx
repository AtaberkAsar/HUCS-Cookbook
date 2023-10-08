import Head from 'next/head';
import Link from 'next/link';

function Home() {
  return (
    <div>
      <Head>
        <title>DevOps Task</title>
      </Head>

      <main>
        <h1>DevOps Task</h1>
        <div>
          <Link href="/login">
            Login
          </Link>
        </div>
        <div>
          <Link href="/register">
            Register
          </Link>
        </div>
        <div>
          <Link href="/dashboard">
            Dashboard
          </Link>
        </div>
      </main>
    </div>
  );
}

export default Home;
