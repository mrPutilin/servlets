package ru.netology.servlet;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.netology.config.JavaConfig;
import ru.netology.controller.PostController;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainServlet extends HttpServlet {

  public static final String PATH_NO_ID = "/api/posts";
  public static final String PATH_WITH_ID = "/api/posts/\\d+";
  private PostController controller;
  private final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(JavaConfig.class);

  @Override
  public void init() {
    Object service = context.getBean("postService");
    Object repository = context.getBean("postRepository");
    controller = (PostController) context.getBean("postController");
  }

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp) {
    // если деплоились в root context, то достаточно этого
    try {
      final var path = req.getRequestURI();
      final var method = req.getMethod();
      // primitive routing
      if (method.equals("GET") && path.equals(PATH_NO_ID)) {
        controller.all(resp);
        return;
      }
      if (method.equals("GET") && path.matches(PATH_WITH_ID)) {
        // easy way
        final var id = controller.parsId(path);
        controller.getById(id, resp);
        return;
      }
      if (method.equals("POST") && path.equals(PATH_NO_ID)) {
        controller.save(req.getReader(), resp);
        return;
      }
      if (method.equals("DELETE") && path.matches(PATH_WITH_ID)) {
        // easy way
        final var id = controller.parsId(path);
        controller.removeById(id, resp);
        return;
      }
      resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
    } catch (Exception e) {
      e.printStackTrace();
      resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
  }

}

