# Problem Statement
Build an MCP(Model Context Protocol) server for `bogleheads.org` forum. I want it to have readonly capability with full text search.

## TODO: MCP Server vs Chatbot Interaction

**Q:** Will I be able to interact with the final app here in the same way I can interact with ChatGPT or any other LLM?

**A:** The MCP server we are building is a **context-provider**, not an LLM.  You will query it over HTTP (e.g. `GET /v1/context?q=roth+conversion&limit=5`) and it will return JSON chunks (text + metadata) from bogleheads.org.  Those chunks are meant to be passed into an LLM prompt to give the model domain knowledge.  If you want a single endpoint that both retrieves context *and* generates answers, you can wrap the MCP server with another thin service that calls your chosen LLM (OpenAI, Claude, etc.) and streams the response back to the client.

> **Action item:** After the retrieval service is live, decide whether to add a chatbot wrapper or integrate directly with your existing LLM workflow.
