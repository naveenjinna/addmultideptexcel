package adddept;

import org.testng.annotations.Test;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeTest;

public class addingdept {

	public static void main(String[] args)
			throws EncryptedDocumentException, InvalidFormatException, IOException, InterruptedException {

		WebDriver driver = new ChromeDriver();
		System.setProperty("webdriver.chrome.driver", "usr/local/bin/chromedriver");
		adddepartment();
	}

	private static WebDriver driver;

	private static final String PAYSLIPS_URL = "http://localhost:8080/employee-payslips/#!/login#employee";

	static HashMap<Object, Object> testresultdata = new HashMap<>();

	@BeforeTest
	public void init() {
		System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
	}

	@Test(priority = 1)
	public static void adddepartment()
			throws IOException, EncryptedDocumentException, InvalidFormatException, InterruptedException {
		WebDriver driver = new ChromeDriver();
		driver.get(PAYSLIPS_URL);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		// LOGIN
		WebElement username = driver.findElement(By.xpath("//*[@id=\"loginForm\"]/fieldset/div[1]/input"));
		username.sendKeys("admin");
		username.submit();
		WebElement password = driver.findElement(By.xpath("//*[@id=\"loginForm\"]/fieldset/div[2]/input"));
		password.sendKeys("admin");
		password.submit();
		WebElement loginbutton = driver.findElement(By.xpath("//*[@id=\"loginForm\"]/fieldset/div[4]/button"));
		loginbutton.click();

		// CLICK ON DEPARTMENT DROPDOWN
		WebElement Department = driver.findElement(By.xpath("//*[@id=\"MainMenu\"]/div[2]/a[1]"));
		Department.click();

		// SELECT ADD DEPARTMENT
		WebElement addDepartment = driver.findElement(By.xpath("//*[@id=\"department\"]/a[1]"));
		addDepartment.click();

		Workbook workbook = WorkbookFactory.create(new File("/home/naveen/seleniumtest/addingdepartments1.xlsx"));

		Sheet sheet = workbook.getSheetAt(0);
		Iterator<org.apache.poi.ss.usermodel.Row> rowIterator = sheet.rowIterator();
		 //System.out.println("#####################"+rowIterator.toString());

		boolean isRowPresent = true;
		while (rowIterator.hasNext() && isRowPresent) {

			// CLICK ON DEPARTMENT DROPDOWN
			WebElement Department1 = driver.findElement(By.xpath("//*[@id=\"MainMenu\"]/div[2]/a[1]"));
			Department1.click();

			// SELECT ADD DEPARTMENT
			WebElement addDepartment1 = driver.findElement(By.xpath("//*[@id=\"department\"]/a[1]"));
			addDepartment1.click();

			WebElement Name = driver.findElement(By.xpath("//*[@id=\"name\"]"));
			Thread.sleep(5000);
			WebElement Description = driver.findElement(By.xpath("//*[@id=\"description\"]"));
			Thread.sleep(5000);
			// WebElement submit =
			// driver.findElement(By.xpath("//*[@id=\"addUserForm\"]/div[16]/div/button"));
			// submit.click();

			Row row = rowIterator.next();
			boolean flag = false;
			if (row.getCell(0) != null) {
				Name.sendKeys(row.getCell(0).toString());
				flag = true;
			}

			if (row.getCell(1) != null)
				Description.sendKeys(row.getCell(1).toString());
			if (flag) {

				WebElement submitbutton = driver.findElement(By.xpath("//*[@id=\"addUserForm\"]/div[3]/div/button"));
				submitbutton.click();
				System.out.println("submit button ");
				Thread.sleep(5000);
				WebDriverWait wait = new WebDriverWait(driver, 300 /* timeout in seconds */);
				try {
					Alert alert = driver.switchTo().alert();
					System.out.println(" Alert Present");
					System.out.println(alert.getText());
					alert.accept();
				} catch (NoAlertPresentException e) {
					System.out.println("No Alert Present");
					String listOfDepartments = driver.findElement(By.xpath(
							 "/html/body/div/div[3]/div/div/div/div/div/div[1]/h2")).getText()
							 .toString(); System.out.println("***************" + listOfDepartments);
							System.out.println("Departments Details added Successfully");
						
				}
				

				/*
				 * if(wait.until(ExpectedConditions.alertIsPresent())==null)
				 * System.out.println("alert was not present"); else {
				 * System.out.println("alert was present"); Alert alert =
				 * driver.switchTo().alert(); System.out.println(alert.getText());
				 * alert.accept(); System.out.println("Exception occured,Duplicate entries"); }
				 * //break;
				 */

				// break;
				/*
				 * // #######################################################
				 * driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
				 * driver.navigate().refresh(); driver.manage().timeouts().implicitlyWait(10,
				 * TimeUnit.SECONDS); // #######################################################
				 */

				/*
				 * WebElement DepartmentInLoop =
				 * driver.findElement(By.xpath("//*[@id=\"department\"]/a[1]"));
				 * DepartmentInLoop.click();
				 */

				/*
				 * WebElement addDepartmentInLoop =
				 * driver.findElement(By.xpath("//*[@id=\\\"department\\\"]/a[1]"));
				 * addDepartmentInLoop.click();
				 */

				/*
				 * WebElement submitbutton =
				 * driver.findElement(By.xpath("//*[@id=\"addUserForm\"]/div[3]/div/button"));
				 * submitbutton.click();
				 */

				// System.out.println("submit button ");

				// workbook.close();

			} else {
				isRowPresent=false;
				workbook.close();
				driver.close();
			}

			/*
			 * try { String listOfDepartments = driver.findElement(By.xpath(
			 * "/html/body/div/div[3]/div/div/div/div/div/div[1]/h2")).getText()
			 * .toString(); System.out.println("***************" + listOfDepartments);
			 * 
			 * System.out.println("Departments Details added Successfully");
			 * Thread.sleep(5000); } catch (Exception ex) {
			 * 
			 * 
			 * } }
			 */
			

		}
	}
}

// driver.close();

// driver.close();
