package ru.rosbank.javaschool.web.servlet;

import ru.rosbank.javaschool.util.SQLTemplate;
import ru.rosbank.javaschool.web.constant.Constants;
import ru.rosbank.javaschool.web.model.ProductModel;
import ru.rosbank.javaschool.web.repository.*;
import ru.rosbank.javaschool.web.service.BurgerAdminService;
import ru.rosbank.javaschool.web.service.BurgerUserService;
import ru.rosbank.javaschool.web.service.EnterService;
import ru.rosbank.javaschool.web.service.EnterServiceImpl;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.*;

import static ru.rosbank.javaschool.web.constant.Constants.ID;
import static ru.rosbank.javaschool.web.constant.Constants.ORDER_ID;

public class FrontServlet extends HttpServlet {
    private BurgerUserService burgerUserService;
    private BurgerAdminService burgerAdminService;
    private EnterService enterService;
    public boolean enterLabel = false;


    @Override
    public void init() throws ServletException {
        log("Init");


        try {

            InitialContext initialContext = new InitialContext();
            DataSource dataSource = (DataSource) initialContext.lookup("java:/comp/env/jdbc/db");
            SQLTemplate sqlTemplate = new SQLTemplate();
            ProductRepository productRepository = new ProductRepositoryJdbcImpl(dataSource, sqlTemplate);
            OrderRepository orderRepository = new OrderRepositoryJdbcImpl(dataSource, sqlTemplate);
            OrderPositionRepository orderPositionRepository = new OrderPositionRepositoryJdbcImpl(dataSource, sqlTemplate);
            AdminsRepository adminsRepository = new AdminsRepositoryJdbcImpl(dataSource, sqlTemplate);
            burgerUserService = new BurgerUserService(productRepository, orderRepository, orderPositionRepository);
            burgerAdminService = new BurgerAdminService(productRepository, orderRepository, orderPositionRepository);
            enterService = new EnterServiceImpl(adminsRepository);
            insertInitialData(adminsRepository);

        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    private void insertInitialData(AdminsRepository adminsRepository) {
        adminsRepository.save("IvanD18", "qwer1234");
        adminsRepository.save("Tiger01", "0000");

    }


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String rootUrl = req.getContextPath().isEmpty() ? "/" : req.getContextPath();
        String url = req.getRequestURI().substring(req.getContextPath().length());

        String login;
        String password;
        int idOfOrder = 0;
        if (url.startsWith("/enter")) {
            if (req.getMethod().equals("GET")) {

                req.getRequestDispatcher("/WEB-INF/enterAsAdmin.jsp").forward(req, resp);
                return;
            }

            if (req.getMethod().equals("POST")) {


                login = req.getParameter("login");
                password = req.getParameter("password");

                enterLabel = enterService.enter(login, password);


                System.out.println(enterLabel);
                resp.sendRedirect(url);
                return;
            }
            return;
        }


        if (url.startsWith("/admin")) {
            if (enterLabel) {
                if (url.equals("/admin")) {

                    if (req.getMethod().equals("GET")) {
                        req.setAttribute(Constants.ITEMS, burgerAdminService.getAll());
                        req.getRequestDispatcher("/WEB-INF/admin/frontpage.jsp").forward(req, resp);
                        return;
                    }

                    if (req.getMethod().equals("POST")) {

                        int id = Integer.parseInt(req.getParameter(ID));
                        String name = req.getParameter("name");
                        int price = Integer.parseInt(req.getParameter("price"));
                        int quantity = Integer.parseInt(req.getParameter("quantity"));
                        String imageUrl = req.getParameter("image_url");
                        String description = req.getParameter("description");
                        burgerAdminService.save(new ProductModel(id, name, price, quantity, imageUrl, description));
                        resp.sendRedirect(url);
                        return;
                    }
                }

                if (url.startsWith("/admin/edit")) {
                    if (req.getMethod().equals("GET")) {
                        // ?id=value
                        int id = Integer.parseInt(req.getParameter(ID));
                        req.setAttribute(Constants.ITEM, burgerAdminService.getById(id));
                        req.setAttribute(Constants.ITEMS, burgerAdminService.getAll());
                        req.getRequestDispatcher("/WEB-INF/admin/frontpage.jsp").forward(req, resp);
                        return;
                    }
                }
            } else {
                req.getRequestDispatcher("/WEB-INF/enter.jsp").forward(req, resp);
            }
        }

        if (url.equals("/")) {

            if (req.getMethod().equals("GET")) {
                HttpSession session = req.getSession();
                if (session.isNew()) {
                    int orderId = burgerUserService.createOrder();
                    session.setAttribute(ORDER_ID, orderId);
                }

                int orderId = (Integer) session.getAttribute(ORDER_ID);
                req.setAttribute("ordered-items", burgerUserService.getAllOrderPosition(orderId));
                req.setAttribute(Constants.ITEMS, burgerUserService.getAll());
                req.getRequestDispatcher("/WEB-INF/frontpage.jsp").forward(req, resp);
                return;
            }
            if (req.getMethod().equals("POST")) {
                HttpSession session = req.getSession();
                if (session.isNew()) {
                    int orderId = burgerUserService.createOrder();

                    session.setAttribute(ORDER_ID, orderId);
                }

                int orderId = (Integer) session.getAttribute(ORDER_ID);

                int id = Integer.parseInt(req.getParameter(ID));
                int quantity = Integer.parseInt(req.getParameter("quantity"));

                burgerUserService.order(orderId, id, quantity);
                resp.sendRedirect(url);
                return;
            }

        }
        if (url.startsWith("/details")) {

            if (req.getMethod().equals("GET")) {

                int id = Integer.parseInt(req.getParameter(ID));
                System.out.println(id);
                req.setAttribute(Constants.ITEM, burgerAdminService.getById(id));
                req.getRequestDispatcher("/WEB-INF/description.jsp").forward(req, resp);
                return;
            }

        }

        if (url.startsWith("/basket")) {
            if (url.equals("/basket")) {


                if (req.getMethod().equals("GET")) {

                    HttpSession session = req.getSession();
                    int orderId = (Integer) session.getAttribute(ORDER_ID);
                    System.out.println(orderId);
                    req.setAttribute(Constants.ITEMS, burgerUserService.getAllOrderPosition(orderId));

                    req.getRequestDispatcher("/WEB-INF/basket.jsp").forward(req, resp);
                    return;
                }
            }
            if (url.startsWith("/basket/remove")) {
                if (req.getMethod().equals("GET")) {
                    // ?id=value
                    int id = Integer.parseInt(req.getParameter(ID));
                    burgerUserService.removeById(id);
                    HttpSession session = req.getSession();
                    int orderId = (Integer) session.getAttribute(ORDER_ID);

                    req.setAttribute(Constants.ITEMS, burgerUserService.getAllOrderPosition(orderId));
                    System.out.println(id);
                    req.getRequestDispatcher("/WEB-INF/basket.jsp").forward(req, resp);
                    return;
                }
            }

        }
    }

    @Override
    public void destroy() {
        log("destroy");
    }

}
