# POC ClojureScript AWS Lambda Function

## Usage

Create a Lambda function in your AWS account: 

```
$ zip -r lambda.zip index.js node_modules out
$ aws lambda upload-function \
  --region us-west-2 \
  --function-name dns-resolver \
  --function-zip ./lambda.zip \
  --timeout 10 \
  --runtime nodejs \
  --handler handler \ 
  --mode event \
  --role arn:aws:iam::<your AWS account ID>:role/lambda_exec_role
```

Test the Lambda in the AWS console, with the following input:

```
{
  "domain": "example.com",
  "type": "A"
}
```

## DIY

1. `lein new mies-node my-lambda`
2. Create an `index.js` to export your function
3. `zip -r lambda.zip index.js node_modules out`
