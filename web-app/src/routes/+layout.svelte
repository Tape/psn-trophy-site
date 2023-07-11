<script lang="ts">
  import type { PageData } from './$types';
  import './styles.css';

  export let data: PageData;
</script>

<nav>
  <ul>
    {#if data?.user}
      <li>
        <form action="/logout" method="POST">
          <button type="submit">Log Out</button>
        </form>
      </li>
    {:else}
      <li><a href="/register">Register</a></li>
      <li><a href="/login">Log In</a></li>
    {/if}
  </ul>
</nav>
<main>
  <slot />
</main>
