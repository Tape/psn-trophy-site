<script lang="ts">
  import type { ActionData, PageData } from './$types';

  export let data: PageData;
  export let form: ActionData;
</script>

{#if form?.success}
  <p>Successfully logged in!</p>
{/if}

{#if data?.user}
  <h1>Hello, {data.user.id}</h1>
  <p>You're already logged in!</p>
{:else}
  <h1>Account Login</h1>
  <form method="POST">
    <label>
      PSN ID
      <input type="text" name="psn_id" />
      Password
      <input type="password" name="password" />
    </label>
    <button type="submit">Submit</button>
  </form>
{/if}

<style>
  input {
    display: block;
    width: 100%;
    margin-bottom: 1rem;
  }
</style>
